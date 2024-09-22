# Flight Reservation Test

<br>
<br>

## 목차

- [소개](#소개)

- [개발 환경](#개발-환경)

- [시스템 아키텍처](#시스템-아키텍처)

- [API 명세서](#api-명세서)

- [ERD 구조](#erd-구조)

- [성능 테스트](#성능-테스트)

  - [Entity dto 간 변환 방법](#entity-dto-간-변환-방법)

  - [예약 동시성 이슈](#예약-동시성-이슈)

    

## 소개

**Flight Reservation Test**는 실무에서 배운 개념들을 통해 더 효율적인 비즈니스 로직을 고민해보고 항공 예약에서 발행할 수 있는 문제들을 성능테스트를 통해 해결 방안을 찾는 프로젝트입니다. <br>



## 개발 환경

- Windows
- IntelliJ
- GitHub
- AWS

## 사용 기술

**백엔드**

- Java 17 openjdk
- SpringBoot 3.3.0
- Spring Data JPA
- Lombok

**빌드 툴**

- Gradle 8.7

**데이터베이스**

- Mysql
- AWS ElasitcCache for Redis

**인프라**

- AWS EC2
- AWS S3
- AWS RDS
- AWS LoadBalancer
- AWS Route 53
- Jenkins
- Docker

**테스트 툴**

- Jmeter

## 시스템 아키텍처

![flight_reservation](https://github.com/user-attachments/assets/046073c3-c2df-49f5-9ed1-a84a579c8d7b)

## API 명세서

|  Index&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   | Fuction&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| HTTP Method |                 API Path                 | Token |       request.pathvariable       | request.queryparam |                         request.body                         | reponse.body  |
| :------: | ---------------- | :---------: | :--------------------------------------: | :---: | :------------------------------: | :----------------: | :----------------------------------------------------------: | :-----------: |
|  [회원]  | 회원가입         |    POST     |               /api/members               |   x   |                                  |                    | { "memberId" : "hgdong", "pw" : "hgdongpw", "name" : "홍길동", "birth" : "2001-02-13T15:30:00", "email" : " [hgdong@gmail.com](mailto:hgdong@gmail.com)", "nationality" : "korea" } | 200, 400, 500 |
|          | 로그인           |    POST     |            /api/members/login            |   x   |                                  |                    |         { "memberId": "hgdong", "pw" : "hgdongpw" }          |               |
|  [예약]  | 예약내역조회     |     GET     |       /api/reservations/{memberId}       |   o   |     { memberId : “hgdong” }      |                    |                                                              |               |
|          | 예약추가         |    POST     |            /api/reservations             |   o   |                                  |                    | { "memberId": "hgdong" "flightId" : "2" "reservationDate" : "2001-02-13T15:30:00", "state" : "Y" } |               |
|          | 예약취소         |    PATCH    |    /api/reservations/{reservationId}     |   o   |      { reservationId : 1 }       |                    |                                                              |               |
|          | 특정날짜예약조회 |     GET     | /api/reservations/{reservationDate}/date |   o   | { reservationDate : “20210213” } |                    |                                                              |               |
| [항공기] | 항공기전체조회   |     GET     |              /api/airplanes              |   o   |                                  |                    |                                                              |               |
|          | 특정항공기조회   |     GET     |       /api/airplanes/{airplaneId}        |   o   |       { airplaneId : “1” }       |                    |                                                              |               |
|          | 항공기추가       |    POST     |              /api/airplanes              |   o   |                                  |                    | { airplaneId - autoIncrement capacity : 10, airplaneType : “보잉 777” } |               |
|          | 항공기변경       |    PATCH    |       /api/airplanes/{airplaneId}        |   o   |       { airplaneId : “1” }       |                    | { "capacity":"100", "airplaneType":"보잉777", "useYN":"Y" }  |               |
|          | 항공기취소       |    PATCH    |    /api/airplanes/{airplaneId}/delete    |   o   |       { airplaneId : “1” }       |                    |                      { "useyn" : “N”  }                      |               |
| [항공편] | 항공편전체조회   |     GET     |               /api/flights               |   o   |                                  |                    |                                                              |               |
|          | 특정항공편조회   |     GET     |         /api/flights/{flightId}          |   o   |        { fligthId : “1” }        |                    |                                                              |               |
|          | 항공편추가       |    POST     |               /api/flights               |   o   |                                  |                    | { "airplaneId" : "1", "departure" : "홍콩", "destination" : "서울" , "outbound" : "2024-07-10T15:30:00", "inbound" : "2024-07-20T15:30:00", "state" : "Y", "price" : "1000000", ”capacity" : “100” } |               |
|          | 항공편변경       |    PATCH    |         /api/flights/{flightId}          |   o   |        { fligthId : “1” }        |                    |                     { price : “500000” }                     |               |
|          | 항공편취소       |    PATCH    |      /api/flights/{flightId}/delete      |   o   |        { fligthId : “1” }        |                    |                                                              |               |

## 
## ERD-구조

![flight_reservation_erd](https://github.com/user-attachments/assets/67d4f054-c3ba-42d3-837a-20ae87a2d3b7)



## 성능 테스트



### Entity dto 간 변환 방법

Entity를 외부에 노출하지 않고, DTO를 통해 데이터를 받음으로써 계층 간의 책임 분리와 유지보수성 향상을 위해  여러 방법을 조사한 뒤 성능테스트를 통해 한가지 방법을 선택했습니다.

1. BeanUtils.copyProperties()
2. Builder 패턴
3. 정적 팩토리 메서드

**10만개의 데이터를 기반으로 Entity를 Dto로 변환하는 로직에 대해 성능 비교**

**1. BeanUtils.copyProperties() - 평균 56ms**

```java
@Override
public List<AirplaneDto> getAllAirplanes(){
    List<Airplane> airplanes = airplaneRepository.findAllByState("Y").orElseThrow(() -> new RuntimeException("상태가 Y인 항공기 리스트 조회 실패"));
    List<AirplaneDto> airplaneDtos = new ArrayList<>();
    AirplaneDto airplaneDto = new AirplaneDto();
    long startTime = System.currentTimeMillis();
    for (Airplane airplane : airplanes) {
        BeanUtils.copyProperties(airplane, airplaneDto);
        airplaneDtos.add(airplaneDto);
    }
    long endTime = System.currentTimeMillis();
    System.out.println("BeanUtils - 걸린시간: " + (endTime - startTime) + "ms");
    return airplaneDtos;
}
```

![스크린샷 2024-08-28 001654](https://github.com/user-attachments/assets/232a101b-90fc-4286-a9dd-fda63f9c7542)



**2. Builder 패턴 - 평균 21ms**

```java
@Override
public List<AirplaneDto> getAllAirplanes(){
    List<Airplane> airplanes = airplaneRepository.findAllByState("Y").orElseThrow(() -> new RuntimeException("상태가 Y인 항공기 리스트 조회 실패"));
    List<AirplaneDto> airplaneDtos = new ArrayList<>();
    long startTime = System.currentTimeMillis();
    for (Airplane airplane : airplanes) {
        airplaneDtos.add(AirplaneDto.toDto(airplane));
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Builder 패턴 - 걸린시간: " + (endTime - startTime) + "ms");
    return airplaneDtos;
}
```

![스크린샷 2024-08-28 001503](https://github.com/user-attachments/assets/d8bc5f20-3edf-4ac1-98df-5ba4063deff1)



**3.정적팩토리 메서드 - 평균 16ms**

```java
@Override
public List<AirplaneDto> getAllAirplanes(){
    List<Airplane> airplanes = airplaneRepository.findAllByState("Y").orElseThrow(() -> new RuntimeException("상태가 Y인 항공기 리스트 조회 실패"));
    List<AirplaneDto> airplaneDtos = new ArrayList<>();
    long startTime = System.currentTimeMillis();
    for (Airplane airplane : airplanes) {
        airplaneDtos.add(AirplaneDto.from(airplane));
    }
    long endTime = System.currentTimeMillis();
    System.out.println("정적팩토리 메서드 - 걸린시간: " + (endTime - startTime) + "ms");
    return airplaneDtos;
}
```

![스크린샷 2024-08-28 000531](https://github.com/user-attachments/assets/d5329f10-56c4-4b49-92c2-a70c630252a2)



비즈니스 로직에 대한 성능 테스트 결과, 다른 방법들에 비해 평균 2배 이상 빠르고, 객체 재사용성을 통한 낮은 메모리 비용과 유지보수성으로 정적 팩토리 메서드 방식을 최종적으로 적용하였습니다.



### 예약 동시성 이슈

항공기 테이블의 수용인원에 따른 항공편 예약 동시 요청이 들어오는 경우에 대해 분산시스템에 대해 DB무결성을 지키기 위한 여러 방법을 조사해 성능테스트를 통해 한가지 방법을 선택하게 되었습니다.

**1. Redisson**

Pub-Sub 기반의 채널을 하나 만들고 스레드의 락 획득시간과 제어시간을 설정해 구현하는 방식

**2. Lettuce**

Redis의 setnx 명령어를 이용한 spin lock 방식

Lettuce로 분산락을 사용하기 위해서는 재사용에 대한 로직을 직접 구현하지만, Redisson은 별도의 Lock interface를 지원해 락에 대한 점유 시간 설정을 커스텀하게 지원해 락을 안전하게 사용할 수 있는 Redisson 방식을 최종적으로 적용하게 되었습니다.

```java
public void saveReservation(ReservationDto reservationDto) {

        String threadName = Thread.currentThread().getName();

        // 락 획득 시도
        String lockName = "FLIGHT_" + reservationDto.getFlightId(); 
        RLock rLock = redissonClient.getLock(lockName); // Rlock 객체 가져옴

        long waitTime = 5L; // 스레드가 락을 획득하기 위해 기다릴 수 있는 최대 시간
        long leaseTime = 3L; // 스레드가 락을 획득한 후 유지할 수 있는 시간
        TimeUnit timeUnit = TimeUnit.SECONDS;

        try {
            // 락 획득 시도 - 동시요청에 대한 스레드들의 락 획득 시도
            boolean available = rLock.tryLock(waitTime, leaseTime, timeUnit); 
            if (!available) {
                throw new RestApiException(CommonErrorCode.LOCK_NOT_AVAILABLE);
            }

            // 락을 획득한 후에 자원 상태를 확인
            Flight flight = flightRepository.findByFlightId(reservationDto.getFlightId());
            Member member = memberRepository.findByMemberId(reservationDto.getMemberId());
            System.out.println(member.getMemberId());
            reservationDto.setPrice(flight.getPrice());

            int currentCapacity = flight.getCapacity();
            log.info("Thread {}: Current capacity before decrement: {}", threadName, currentCapacity);

            if (currentCapacity > 0) {
                flight.setCapacity(currentCapacity - 1);
                Reservation reservation = Reservation.createFromDto(member, flight, reservationDto);
                log.info("Thread {}: Capacity decremented, new capacity: {}", threadName, flight.getCapacity());

                reservationRepository.save(reservation);

                ReservationHistory reservationHistory = ReservationHistory.createFromDto(member, flight, reservationDto);
                reservationHistoryRepository.save(reservationHistory);

            } else {
                throw new RestApiException(CommonErrorCode.FLIGHT_FULL);
            }

        } catch (InterruptedException e) {
            throw new RestApiException(CommonErrorCode.LOCK_INTERRUPTED_ERROR);
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
                log.info("unlock complete: {}", rLock.getName());
            }
        }
    }
```

