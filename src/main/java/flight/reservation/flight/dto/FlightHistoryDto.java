package flight.reservation.flight.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlightHistoryDto {

    //항공편 ID
    Integer flightId;

    //출발지
    String departure;

    //도착지
    String destination;

    //가는편
    LocalDate outbound;

    //오는편
    LocalDate inbound;

    //생성일자
    LocalDate createdDate;

    //변경일자
    LocalDate updatedDate;
    //가격
    Integer price;

    //상태
    String state;

}
