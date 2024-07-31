package flight.reservation.flight.service;

import flight.reservation.flight.dto.FlightDto;
import flight.reservation.flight.dto.FlightHistoryDto;
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
//            FlightDto flightDto = new FlightDto();
//            flightDto.setFlightId(flight.getFlightId());
//            flightDto.setDeparture(flight.getDeparture());
//            flightDto.setOutbound(flight.getOutbound());
//            flightDto.setInbound(flight.getInbound());
//            flightDto.setCreatedDate(flight.getCreatedDate());
//            flightDto.setUpdatedDate(flight.getUpdatedDate());
//            flightDto.setPrice(flight.getPrice());
//            flightDto.setState(flight.getState());
            FlightDto flightDto = FlightDto.fromEntity(flight);
            flightDtoList.add(flightDto);
        }

        return flightDtoList;
    }

    public void saveFlight(FlightDto flightDto){
        Airplane airplane = airplaneRepository.findByAirplaneId(flightDto.getAirplaneId());
        Flight flight = Flight.createFromDto(airplane,flightDto);
//        Flight flight = new Flight();
//        flight.setAirplane(airplane);
//        flight.setDeparture(flightDto.getDeparture());
//        flight.setDestination(flightDto.getDestination());
//        flight.setOutbound(flightDto.getOutbound());
//        flight.setInbound(flightDto.getInbound());
//        flight.setState(flightDto.getState());
//        flight.setPrice(flightDto.getPrice());

        Flight flight_flightHistory = flightRepository.save(flight);

        FlightHistory flightHistory = FlightHistory.createFromDto(flight_flightHistory,airplane,flightDto);
//        FlightHistory flightHistory = new FlightHistory();
//        flightHistory.setFlight(flight_flightHistory);
//        flightHistory.setAirplane(airplane);
//        flightHistory.setDeparture(flightDto.getDeparture());
//        flightHistory.setDestination(flightDto.getDestination());
//        flightHistory.setOutbound(flightDto.getOutbound());
//        flightHistory.setInbound(flightDto.getInbound());
//        flightHistory.setState(flightDto.getState());
//        flightHistory.setPrice(flightDto.getPrice());
        flightHistoryRepository.save(flightHistory);
    }

    //항공기/항공기 이력 변경
    public FlightDto updateFlight(Integer flightId, FlightDto flightDto){
        Flight flight = flightRepository.findByFlightId(flightId);
        flight.updateFlightDetails(flightDto);
//        flight.setDeparture(flightDto.getDeparture());
//        flight.setDestination(flightDto.getDestination());
//        flight.setOutbound(flightDto.getOutbound());
//        flight.setInbound(flightDto.getInbound());
//        flight.setPrice(flightDto.getPrice());

        flightRepository.save(flight);

        FlightHistory flightHistory = flightHistoryRepository.findByFlightHistoryId(flightId);
        flightHistory.updateFlightHistoryDetails(flight,flightDto);
//        flightHistory.setFlight(flight);
//        flightHistory.setDeparture(flightDto.getDeparture());
//        flightHistory.setDestination(flightDto.getDestination());
//        flightHistory.setOutbound(flightDto.getOutbound());
//        flightHistory.setInbound(flightDto.getInbound());
//        flightHistory.setState(flightDto.getState());
//        flightHistory.setPrice(flightDto.getPrice());
        flightHistoryRepository.save(flightHistory);

        return new FlightDto(flight.getFlightId(),flight.getAirplane().getAirplaneId(),flight.getDeparture(),flight.getDestination(),flight.getOutbound(),flight.getInbound(),flight.getCreatedDate(),flight.getUpdatedDate(),flight.getPrice(),flight.getState());
    }
    
    //항공기/항공기 이력 취소
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
        return new FlightDto(flight.getFlightId(),flight.getAirplane().getAirplaneId(),flight.getDeparture(),flight.getDestination(),flight.getOutbound(),flight.getInbound(),flight.getCreatedDate(),flight.getUpdatedDate(),flight.getPrice(),flight.getState());
    }
}