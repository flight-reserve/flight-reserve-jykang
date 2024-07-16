package flight.reservation.reservation.controller;

import flight.reservation.reservation.entity.Reservation;
import flight.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    //예약 내역조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<Reservation>> inquiryReservation(@PathVariable("memberId") int memberId){
        return new ResponseEntity<>(reservationService.inquiryFlight(memberId), HttpStatus.OK);
    }

    //예약 추가
    @PostMapping("/")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation){
        return new ResponseEntity<>(reservationService.saveReservation(reservation),HttpStatus.OK);
    }

    //예약 취소
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Reservation> changeReservation(@PathVariable("reservationId") int reservationId){
        return new ResponseEntity<>(reservationService.changeReservation(reservationId),HttpStatus.OK);
    }

    //특정 날짜예약 조회
    @GetMapping("/{reservationData}")
    public ResponseEntity<List<Reservation>> inquirySpecificReservation(@PathVariable("reservationData") Date reservationData) {
        return new ResponseEntity<>(reservationService.inquirySpecificReservation(reservationData),HttpStatus.OK);
    }


}
