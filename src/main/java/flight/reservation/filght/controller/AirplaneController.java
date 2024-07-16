package flight.reservation.filght.controller;

import flight.reservation.filght.dto.AirplaneDto;
import flight.reservation.filght.entity.Airplane;
import flight.reservation.filght.service.AirplaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;

    //항공기 전체조회
    @GetMapping("/")
    public ResponseEntity<List<Airplane>> inquiryFlight(){
        return new ResponseEntity<>(airplaneService.inquiryFlight(), HttpStatus.OK);
    }

    //항공기 추가
    @PostMapping("/")
    public ResponseEntity<Airplane> addFlight(@RequestBody Airplane airplane){
        return new ResponseEntity<>(airplaneService.saveFlight(airplane),HttpStatus.OK);
    }

    //항공기 변경
    @PatchMapping("/{airplaneId}")
    public ResponseEntity<AirplaneDto> changeFlight(@PathVariable("airplaneId") int airplaneId, @RequestBody AirplaneDto airplaneDto) {
        AirplaneDto changeFlight = airplaneService.updateFlight(airplaneId,airplaneDto);
        return new ResponseEntity<>(changeFlight,HttpStatus.OK);
    }
    
    //항공기 취소
    @DeleteMapping("/{airplaneId}")
    public ResponseEntity<Airplane> deleteFlight(@PathVariable("airplaneId") int airplaneId) {
        return new ResponseEntity<>(airplaneService.deleteFlight(airplaneId),HttpStatus.OK);
    }

    //특정 항공기 조회
    @GetMapping("/{airplaneId}")
    public ResponseEntity<Airplane> inquirySpecificFlight(@PathVariable("airplaneId") int airplaneId) {
        return new ResponseEntity<>(airplaneService.inquirySpecificFlight(airplaneId),HttpStatus.OK);
    }


}
