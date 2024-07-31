package flight.reservation.flight.repository;


import flight.reservation.flight.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AirplaneRepository extends JpaRepository<Airplane, Integer> {

    Airplane findByAirplaneId(Integer airplaneId);
    List<Airplane> findAllByUseyn(String useYn);

}
