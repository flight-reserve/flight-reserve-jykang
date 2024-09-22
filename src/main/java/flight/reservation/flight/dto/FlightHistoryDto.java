package flight.reservation.flight.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlightHistoryDto {

    //항공편 ID
    private Integer flightId;

    //출발지
    private String departure;

    //도착지
    private String destination;

    //가는편
    private LocalDate outbound;

    //오는편
    private LocalDate inbound;

    //수용가능인원
    private Integer capacity;

    //생성일자
    private LocalDate createdDate;

    //변경일자
    private LocalDate updatedDate;
    //가격
    private Integer price;

    //상태
    private String state;

}
