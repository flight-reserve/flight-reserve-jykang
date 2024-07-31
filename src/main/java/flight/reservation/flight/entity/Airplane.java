package flight.reservation.flight.entity;

import flight.reservation.flight.dto.AirplaneDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Airplane {

    // 항공기ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer airplaneId;

    // 연관관계 설정
    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL)
    private List<Flight> flights;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL)
    private List<FlightHistory> flightHistories;

    // 수용가능인원
    private Integer capacity;

    // 항공기종류
    @Column(length = 20)
    private String airplaneType;

    // 사용여부
    @Column(length = 1)
    @ColumnDefault("'Y'")
    private String useyn;

    // 생성자
    private Airplane(Integer capacity, String airplaneType) {
        this.capacity=capacity;
        this.airplaneType=airplaneType;
    }


    // 정적 팩토리 메서드: DTO에서 엔티티로 변환
    public static Airplane createFromDto(AirplaneDto airplaneDto) {
        return new Airplane(airplaneDto.getCapacity(), airplaneDto.getAirplaneType());
    }

    public void updateFlightDetails(Integer capacity, String airplaneType){
        this.capacity = capacity;
        this.airplaneType=airplaneType;
    }
}
