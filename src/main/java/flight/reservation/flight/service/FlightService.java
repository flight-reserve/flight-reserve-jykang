package flight.reservation.flight.service;

import flight.reservation.flight.dto.FlightDto;
import flight.reservation.flight.entity.Airplane;
import flight.reservation.flight.entity.Flight;
import flight.reservation.flight.entity.FlightHistory;
import flight.reservation.flight.repository.AirplaneRepository;
import flight.reservation.flight.repository.FlightHistoryRepository;
import flight.reservation.flight.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FlightService {
    private final FlightRepository flightRepository;

    private final AirplaneRepository airplaneRepository;
    private final FlightHistoryRepository flightHistoryRepository;

    public List<FlightDto> inquiryFlight(){
        List<Flight> flights = flightRepository.findAllByState("Y");
        List<FlightDto> flightDtoList = new ArrayList<>();

        for (Flight flight : flights) {

            FlightDto flightDto = FlightDto.fromEntity(flight);
            flightDtoList.add(flightDto);
        }

        return flightDtoList;
    }

    //항공편 추가
    public void saveFlight(FlightDto flightDto){
        Airplane airplane = airplaneRepository.findByAirplaneId(flightDto.getAirplaneId());
        Flight flight = Flight.createFromDto(airplane,flightDto);

        Flight flight_flightHistory = flightRepository.save(flight);

        FlightHistory flightHistory = FlightHistory.createFromDto(flight_flightHistory,airplane,flightDto);

        flightHistoryRepository.save(flightHistory);
    }

    //항공편/항공편 이력 변경
    public FlightDto updateFlight(Integer flightId, FlightDto flightDto){
        Flight flight = flightRepository.findByFlightId(flightId);
        flight.updateFlightDetails(flightDto);

        flightRepository.save(flight);

        FlightHistory flightHistory = flightHistoryRepository.findByFlightHistoryId(flightId);
        flightHistory.updateFlightHistoryDetails(flight,flightDto);

        flightHistoryRepository.save(flightHistory);

        return new FlightDto(flight.getFlightId(),flight.getAirplane().getAirplaneId(),flight.getDeparture(),flight.getDestination(),flight.getOutbound(),flight.getInbound(),flight.getCapacity(),flight.getPrice(),flight.getState());
    }
    
    //항공편/항공편 이력 취소
    public void deleteFlight(Integer flightId) {
        Flight flight = flightRepository.findByFlightId(flightId);
        flight.setState("N");
        flightRepository.save(flight);

        FlightHistory flightHistory = flightHistoryRepository.findByFlightHistoryId(flightId);
        flightHistory.setState("N");
        flightHistoryRepository.save(flightHistory);
    }

    public FlightDto inquirySpecificFlight(Integer flightId) {
        Flight flight = flightRepository.findByFlightId(flightId);
        return new FlightDto(flight.getFlightId(),flight.getAirplane().getAirplaneId(),flight.getDeparture(),flight.getDestination(),flight.getOutbound(),flight.getInbound(),flight.getCapacity(),flight.getPrice(),flight.getState());
    }
}