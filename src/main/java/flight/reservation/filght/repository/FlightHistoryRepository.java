package flight.reservation.filght.repository;


import flight.reservation.filght.entity.FlightHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FlightHistoryRepository extends JpaRepository<FlightHistory,Integer> {

    Optional<FlightHistory> findByFlightHistoryId(int flightHistoryId);
}
