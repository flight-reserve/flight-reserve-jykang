package flight.reservation.flight.repository;


import flight.reservation.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FlightRepository extends JpaRepository<Flight,Integer> {
  Flight findByFlightId(Integer flightId);
  List<Flight> findAllByState(String state);
}
