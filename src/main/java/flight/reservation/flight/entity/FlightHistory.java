package flight.reservation.flight.entity;

import flight.reservation.flight.dto.FlightDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class) //생성 및 변경일자 자동 기록
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightHistory {

    //항공이력ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer flightHistoryId;

    //항공편ID
    //연관관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Flight flight;

    //항공기ID
    //연관관계 설정
    @ManyToOne
    @JoinColumn
    private Airplane airplane;

    //출발지
    @Column(length = 20)
    private String departure;

    //도착지
    @Column(length = 20)
    private String destination;

    //가는편
    private LocalDateTime outbound;

    //오는편
    private LocalDateTime inbound;

    //생성일자
    @CreatedDate
    private LocalDateTime createdDate;

    //변경일자
    @LastModifiedDate
    private LocalDateTime updatedDate;

    //상태
    @Column(length = 1)
    private String state;

    //가격
    private Integer price;

    // 생성자
    private FlightHistory(Flight flight, Airplane airplane, String departure, String destination, LocalDateTime outbound, LocalDateTime inbound, String state, Integer price) {
        this.flight=flight;
        this.airplane=airplane;
        this.departure=departure;
        this.destination=destination;
        this.outbound=outbound;
        this.inbound=inbound;
        this.state=state;
        this.price=price;

    }


    // 정적 팩토리 메서드: DTO에서 엔티티로 변환
    public static FlightHistory createFromDto(Flight flight,Airplane airplane,FlightDto flightDto) {
        return new FlightHistory(flight, airplane, flightDto.getDeparture(), flightDto.getDestination(), flightDto.getOutbound(), flightDto.getInbound(), flightDto.getState(), flightDto.getPrice());
    }

    // 필드 업데이트 메서드 (private 접근 수준)
    private void updateDetails(Flight flight, String departure, String destination, LocalDateTime outbound, LocalDateTime inbound, String state, Integer price) {
        this.flight=flight;
        this.departure=departure;
        this.destination=destination;
        this.outbound=outbound;
        this.inbound=inbound;
        this.state=state;
        this.price=price;
    }

    // 외부에서 접근 가능한 업데이트 메서드
    public void updateFlightHistoryDetails(Flight flight, FlightDto flightDto) {
        updateDetails(flight, flightDto.getDeparture(), flightDto.getDestination(), flightDto.getOutbound(), flightDto.getInbound(),flightDto.getState(), flightDto.getPrice());
    }

    public void setState(String state) {
        this.state=state;
    }
}
