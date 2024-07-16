package flight.reservation.filght.repository;


import flight.reservation.filght.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;


public interface FlightRepository extends JpaRepository<Flight,Integer> {


  Optional<Flight> findByFlightId(int flightId);


  Optional<Flight> deleteByFlightId(int flightId);
}
