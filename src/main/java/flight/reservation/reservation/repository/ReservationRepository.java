package flight.reservation.reservation.repository;

import flight.reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    List<Reservation> findByMember_MemberId(String memberId);

    Reservation findByReservationId(Integer reservationId);

    List<Reservation> findByReservationDate(LocalDateTime reservationDate);
}
