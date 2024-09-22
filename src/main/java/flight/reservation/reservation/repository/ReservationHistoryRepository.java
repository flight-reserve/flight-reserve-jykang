package flight.reservation.reservation.repository;

import flight.reservation.reservation.entity.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory,Integer> {


}
