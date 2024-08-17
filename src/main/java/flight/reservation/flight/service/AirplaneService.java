package flight.reservation.flight.service;

import flight.reservation.flight.dto.AirplaneDto;
import flight.reservation.flight.entity.Airplane;
import flight.reservation.flight.repository.AirplaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;

    public AirplaneDto saveFlight(AirplaneDto airplaneDto) {

        Airplane airplane = Airplane.createFromDto(airplaneDto);
        Airplane saveAirplane = airplaneRepository.save(airplane);
        return new AirplaneDto(saveAirplane.getAirplaneId(), saveAirplane.getCapacity(), saveAirplane.getAirplaneType(), saveAirplane.getUseyn());
    }

    //항공기 조회  - Y인 경우만 조회
    public List<AirplaneDto> inquiryFlight() {
        List<Airplane> airplanes = airplaneRepository.findAllByUseyn("Y");
        List<AirplaneDto> airplaneDtoList = new ArrayList<>();

        for (Airplane airplane : airplanes) {

            AirplaneDto airplaneDto = AirplaneDto.fromEntity(airplane);

            airplaneDtoList.add(airplaneDto);
        }
        return airplaneDtoList;
    }

    //항공기변경
    public AirplaneDto updateFlight(Integer airplaneId, AirplaneDto airplaneDto) {
        Airplane airplane = airplaneRepository.findByAirplaneId(airplaneId);
        airplane.updateFlightDetails(airplaneDto.getCapacity(), airplaneDto.getAirplaneType());

        Airplane updatedAirplane = airplaneRepository.save(airplane);
        
        return new AirplaneDto(updatedAirplane.getAirplaneId(), updatedAirplane.getCapacity(), updatedAirplane.getAirplaneType(), updatedAirplane.getUseyn());
    }

    public AirplaneDto deleteFlight(Integer airplaneId) {
        Airplane airplane = airplaneRepository.findByAirplaneId(airplaneId);
        Airplane deletePlane = airplaneRepository.save(airplane);
        return new AirplaneDto(deletePlane.getAirplaneId(), deletePlane.getCapacity(), deletePlane.getAirplaneType(), deletePlane.getUseyn());
    }

    public AirplaneDto inquirySpecificFlight(Integer airplaneId) {
        Airplane airplane = airplaneRepository.findByAirplaneId(airplaneId);
        return new AirplaneDto(airplane.getAirplaneId(), airplane.getCapacity(), airplane.getAirplaneType(), airplane.getUseyn());
    }
}
