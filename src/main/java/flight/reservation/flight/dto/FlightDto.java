package flight.reservation.flight.dto;

import flight.reservation.flight.entity.Flight;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String departure;

    //도착지
    @NotBlank
    private String destination;

    //가는편
    @NotNull
    private LocalDateTime outbound;

    //오는편
    @NotNull
    private LocalDateTime inbound;

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
                flight.getPrice(),
                flight.getState()
        );
    }

}
