package flight.reservation.flight.dto;

import flight.reservation.flight.entity.Flight;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    //항공편 ID
    private Integer flightId;

    //항공기
    private Integer airplaneId;

    //출발지
    private String departure;

    //도착지
    private String destination;

    //가는편
    private LocalDateTime outbound;

    //오는편
    private LocalDateTime inbound;

    //생성일자
    private LocalDateTime createdDate;

    //변경일자
    private LocalDateTime updatedDate;

    //가격
    private Integer price;

    //상태
    private String state;

    // 정적 팩토리 메서드: 엔티티에서 DTO로 변환
    public static FlightDto fromEntity(Flight flight) {
        return new FlightDto(
                flight.getFlightId(),
                flight.getAirplane().getAirplaneId(),
                flight.getDeparture(),
                flight.getDestination(),
                flight.getOutbound(),
                flight.getInbound(),
                flight.getCreatedDate(),
                flight.getUpdatedDate(),
                flight.getPrice(),
                flight.getState()
        );
    }


}
