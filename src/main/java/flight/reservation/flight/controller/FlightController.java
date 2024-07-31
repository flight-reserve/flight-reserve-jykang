package flight.reservation.flight.controller;

import flight.reservation.flight.dto.FlightDto;
import flight.reservation.flight.entity.Flight;
import flight.reservation.flight.service.FlightService;
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
    @GetMapping
    public ResponseEntity<List<FlightDto>> inquiryFlight() {
        return new ResponseEntity<>(flightService.inquiryFlight(), HttpStatus.OK);
    }

    //항공편/항공편 이력 추가
    @PostMapping
    public ResponseEntity<String> addFlight(@RequestBody FlightDto flightDto) {
        flightService.saveFlight(flightDto);
        return new ResponseEntity<>("항공편을 추가했습니다", HttpStatus.OK);
    }

    //항공편/항공편 이력 변경
    @PatchMapping("/{flightId}")
    public ResponseEntity<FlightDto> changeFlight(@PathVariable("flightId") int flightId, @RequestBody FlightDto flightDto) {
        return new ResponseEntity<>(flightService.updateFlight(flightId, flightDto), HttpStatus.OK);
    }

    //항공편/항공편 이력 취소
    @PatchMapping("/{flightId}/delete")
    public ResponseEntity<String> deleteFlight(@PathVariable("flightId") int flightId) {
        flightService.deleteFlight(flightId);
        return new ResponseEntity<>("항공편이 취소되었습니다", HttpStatus.OK);
    }

    //특정 항공편 조회
    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDto> inquirySpecificFlight(@PathVariable("flightId") int flightId) {
        return new ResponseEntity<>(flightService.inquirySpecificFlight(flightId), HttpStatus.OK);
    }

}
