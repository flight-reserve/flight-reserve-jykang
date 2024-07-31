package flight.reservation.reservation.controller;

import flight.reservation.reservation.dto.ReservationDto;
import flight.reservation.reservation.entity.Reservation;
import flight.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    //예약 내역조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<ReservationDto>> inquiryReservation(@PathVariable("memberId") String memberId){
        return new ResponseEntity<>(reservationService.inquiryReservation(memberId), HttpStatus.OK);
    }

    //예약 추가
    @PostMapping
    public ResponseEntity<String> addReservation(@RequestBody ReservationDto reservationDto){
        reservationService.saveReservation(reservationDto);
        return new ResponseEntity<>("예약이 추가되었습니다",HttpStatus.OK);
    }

    //예약 취소
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> changeReservation(@PathVariable("reservationId") int reservationId){
        reservationService.changeReservation(reservationId);
        return new ResponseEntity<>("예약 취소되었습니다",HttpStatus.OK);
    }

    //특정 날짜예약 조회
    @GetMapping("/{reservationDate}/date")
    public ResponseEntity<List<ReservationDto>> inquirySpecificReservation(@PathVariable("reservationDate") LocalDateTime reservationDate) {
        return new ResponseEntity<>(reservationService.inquirySpecificReservation(reservationDate),HttpStatus.OK);
    }

}
