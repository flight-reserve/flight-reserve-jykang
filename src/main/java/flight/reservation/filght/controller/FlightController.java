package flight.reservation.filght.controller;

import flight.reservation.filght.dto.FlightDto;
import flight.reservation.filght.entity.Flight;
import flight.reservation.filght.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    //항공편 전체조회
    @GetMapping("/")
    public ResponseEntity<List<Flight>> inquiryFlight() {
        return new ResponseEntity<>(flightService.inquiryFlight(), HttpStatus.OK);
    }

    //항공편 추가
    @PostMapping("/")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.saveFlight(flight), HttpStatus.OK);
    }

    //항공편 변경
    @PatchMapping("/{flightId}")
    public ResponseEntity<FlightDto> changeFlight(@PathVariable("flightId") int flightId, @RequestBody FlightDto flightDto) {
        FlightDto changeFlight = flightService.updateFlight(flightId, flightDto);
        return new ResponseEntity<>(changeFlight, HttpStatus.OK);
    }

    //항공기 취소
    @DeleteMapping("/{flightId}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable("flightId") int flightId) {
        return new ResponseEntity<>(flightService.deleteFlight(flightId), HttpStatus.OK);
    }

    //특정 항공기 조회
    @GetMapping("/{flightId}")
    public ResponseEntity<Flight> inquirySpecificFlight(@PathVariable("flightId") int flightId) {
        return new ResponseEntity<>(flightService.inquirySpecificFlight(flightId), HttpStatus.OK);
    }


}
