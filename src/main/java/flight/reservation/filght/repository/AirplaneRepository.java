package flight.reservation.filght.repository;


import flight.reservation.filght.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirplaneRepository extends JpaRepository<Airplane,Integer> {

    Optional<Airplane> findByAirplaneId(int airplaneId);

    Optional<Airplane> deleteByAirplaneId(int airplaneId);


}
