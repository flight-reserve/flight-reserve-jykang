package flight.reservation.filght.service;

import flight.reservation.filght.dto.AirplaneDto;
import flight.reservation.filght.entity.Airplane;
import flight.reservation.filght.repository.AirplaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;

    public Airplane saveFlight(Airplane airplane){
       return airplaneRepository.save(airplane);
    }

    public List<Airplane> inquiryFlight(){
        return airplaneRepository.findAll();
    }

    //항공기변경 - 이력테이블도 변경필요
    public AirplaneDto updateFlight(int airplaneId, AirplaneDto airplaneDto){
        Airplane airplane = airplaneRepository.findByAirplaneId(airplaneId);
        airplane.setCapacity(airplaneDto.getCapacity());
        airplane.setAirplaneType(airplaneDto.getAirplaneType());
        Airplane updatedAirplane = airplaneRepository.save(airplane);
        return new AirplaneDto(updatedAirplane.getAirplaneId(), updatedAirplane.getCapacity(), updatedAirplane.getAirplaneType());
    }

    public Airplane deleteFlight(int airplaneId){
        return airplaneRepository.deleteByAirplaneId(airplaneId);
    }

    public Airplane inquirySpecificFlight(int airplaneId) {
        return airplaneRepository.findByAirplaneId(airplaneId);
    }
}
