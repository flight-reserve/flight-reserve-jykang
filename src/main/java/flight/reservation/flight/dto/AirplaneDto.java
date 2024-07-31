package flight.reservation.flight.dto;

import flight.reservation.flight.entity.Airplane;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneDto {

    //항공기 ID
    private Integer airplaneId;

    //수용가능인원
    private Integer capacity;

    //항공기종류
    private String airplaneType;

    private String useyn;

    // 정적 팩토리 메서드: 엔티티에서 DTO로 변환
    public static AirplaneDto fromEntity(Airplane airplane) {
        return new AirplaneDto(
                airplane.getAirplaneId(),
                airplane.getCapacity(),
                airplane.getAirplaneType(),
                airplane.getUseyn()

        );
    }

}
