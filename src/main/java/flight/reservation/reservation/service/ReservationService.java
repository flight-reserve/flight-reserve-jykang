package flight.reservation.reservation.service;


import flight.reservation.flight.entity.Flight;
import flight.reservation.flight.repository.FlightRepository;
import flight.reservation.member.entity.Member;
import flight.reservation.member.repository.MemberRepository;
import flight.reservation.reservation.dto.ReservationDto;
import flight.reservation.reservation.entity.Reservation;
import flight.reservation.reservation.entity.ReservationHistory;
import flight.reservation.reservation.repository.ReservationHistoryRepository;
import flight.reservation.reservation.repository.ReservationRepository;
import flight.reservation.util.CommonErrorCode;
import flight.reservation.util.ErrorCode;
import flight.reservation.util.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    private final ReservationHistoryRepository reservationHistoryRepository;

    private final FlightRepository flightRepository;

    private final RedissonClient redissonClient;

    //특정 ID에 대한 예약 조회
    public List<ReservationDto> inquiryReservation(String memberId){
        List<Reservation> findAllReservation= reservationRepository.findByMember_MemberId(memberId);
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for(Reservation reservation : findAllReservation){
            ReservationDto reservationDto = ReservationDto.toDto(reservation);
            reservationDtoList.add(reservationDto);
        }
        return reservationDtoList;
    }

    //예약 추가 - Redisson을 이용해서 테스트 해보기
    // TODO: 2024-08-22 Redisson 이용한 코드 로직 추가
    public void saveReservation(ReservationDto reservationDto) {

        String threadName = Thread.currentThread().getName();

        // 락 획득 시도
        String lockName = "FLIGHT_" + reservationDto.getFlightId(); //락 단위를 문자열로 설정
        RLock rLock = redissonClient.getLock(lockName); // Rlock 객체 가져옴

        // TODO: 스레드 기다릴 수 있는 시간과 락 획득한 후 유지할 수 있는 시간 최적시간 정하기
        long waitTime = 5L; // 스레드가 락을 획득하기 위해 기다릴 수 있는 최대 시간
        long leaseTime = 3L; // 스레드가 락을 획득한 후 유지할 수 있는 시간
        TimeUnit timeUnit = TimeUnit.SECONDS;

        try {
            boolean available = rLock.tryLock(waitTime, leaseTime, timeUnit); // 락 획득 시도 - 동시요청에 대한 스레드들의 락 획득 시도
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


    //예약 취소 - 상태값 변경
    public void changeReservation(int reservationId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        reservation.setState("N");
        reservationRepository.save(reservation);
    }

    //특정 날짜 예약 조회
    public List<ReservationDto> inquirySpecificReservation(LocalDateTime reservationData) {
        List<Reservation> SpecificReservation = reservationRepository.findByReservationDate(reservationData);
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for(Reservation reservation : SpecificReservation){
            ReservationDto reservationDto = ReservationDto.toDto(reservation);
            reservationDtoList.add(reservationDto);
        }
        return reservationDtoList;
    }
}
