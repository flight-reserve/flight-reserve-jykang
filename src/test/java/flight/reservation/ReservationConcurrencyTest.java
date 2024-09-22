package flight.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class ReservationConcurrencyTest {

    @Autowired
    private RestTemplate restTemplate;  // RestTemplate을 사용하여 HTTP 요청을 보냄

    private final String reservationUrl = "http://localhost:8080/api/reservations";

    @Test
    //@Transactional
    public void testConcurrentReservations() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(1);

        Runnable task = () -> {
            try {
                latch.await();  // 다른 스레드들과 함께 대기
                ResponseEntity<String> response = sendReservationRequest();  // 예약 추가 요청 전송
                System.out.println("Response: " + response.getStatusCode() + " - " + response.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(task);  // 각 스레드에 작업 할당
        }

        latch.countDown();  // 모든 스레드에 동시에 시작 신호 보내기
        executorService.shutdown();  // ExecutorService 종료
    }

    private ResponseEntity<String> sendReservationRequest() {
        String requestJson = "{\"memberId\":\"hgdong" + ThreadLocalRandom.current().nextInt(1, 11) + "\",\"flightId\":2,\"reservationDate\":\"2001-02-13T15:30:00\",\"state\":\"Y\"}";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 엔티티 생성 (바디 + 헤더)
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        // POST 요청 전송
        return restTemplate.postForEntity(reservationUrl, request, String.class);
    }
}