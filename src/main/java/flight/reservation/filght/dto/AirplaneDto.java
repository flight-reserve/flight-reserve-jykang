package flight.reservation.filght.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AirplaneDto {

    //항공기 ID
    int airplaneId;

    //수용가능인원
    int capacity;

    //항공기종류
    String airplaneType;

}
