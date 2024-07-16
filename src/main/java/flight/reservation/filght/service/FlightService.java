package flight.reservation.filght.service;

import flight.reservation.filght.dto.FlightDto;
import flight.reservation.filght.entity.Flight;
import flight.reservation.filght.entity.FlightHistory;
import flight.reservation.filght.repository.FlightHistoryRepository;
import flight.reservation.filght.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FlightService {
    private final FlightRepository flightRepository;

    private final FlightHistoryRepository flightHistoryRepository;

    public List<Flight> inquiryFlight(){
        return flightRepository.findAll();
    }

    public Flight saveFlight(Flight flight){
        return flightRepository.save(flight);
    }

    //항공기/항공기 이력 변경
    public FlightDto updateFlight(int flightId, FlightDto flightDto){
        Flight flight = flightRepository.findByFlightId(flightId);
        flight.setFlightId(flightDto.getFlightId());
        Flight updatedFlight = flightRepository.save(flight);

        FlightHistory flightHistory = flightHistoryRepository.findByFlightHistoryId(flightId);
        flightHistory.setFlightHistoryId(flightDto.getFlightId());
        flightHistoryRepository.save(flightHistory);

        return new FlightDto(updatedFlight.getFlightId());
    }

    public Flight deleteFlight(int flightId) {
        return flightRepository.deleteByFlightId(flightId);
    }

    public Flight inquirySpecificFlight(int flightId) {
        return flightRepository.findByFlightId(flightId);
    }
}