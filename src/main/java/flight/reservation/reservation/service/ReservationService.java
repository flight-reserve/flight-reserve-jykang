package flight.reservation.reservation.service;

import flight.reservation.filght.dto.AirplaneDto;
import flight.reservation.filght.entity.Airplane;
import flight.reservation.reservation.dto.ReservationDto;
import flight.reservation.reservation.entity.Reservation;
import flight.reservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> inquiryFlight(int memberId){
        return reservationRepository.findByMemberMemberId(memberId);
    }

    //예약 추가
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    //예약 취소 - 상태값 변경
    public Reservation changeReservation(int reservationId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        reservation.setState("N");
        return reservation;
    }

    //특정 날짜 예약 조회
    public List<Reservation> inquirySpecificReservation(Date reservationData) {
        return reservationRepository.findByReservationDate(reservationData);
    }
}
