package flight.reservation.reservation.repository;

import flight.reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    Optional<List<Reservation>> findByMemberMemberId(int memberId);

    Optional<Reservation> findByReservationId(int reservationId);

    Optional<List<Reservation>> findByReservationDate(Date reservationDate);
}
