package flight.reservation.flight.repository;


import flight.reservation.flight.entity.FlightHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlightHistoryRepository extends JpaRepository<FlightHistory, Integer> {

    FlightHistory findByFlightHistoryId(Integer flightHistoryId);
}
