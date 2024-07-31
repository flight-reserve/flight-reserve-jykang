package flight.reservation.flight.entity;

import flight.reservation.flight.dto.FlightDto;
import flight.reservation.reservation.entity.Reservation;
import flight.reservation.reservation.entity.ReservationHistory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@EntityListeners(AuditingEntityListener.class) //생성 및 변경일자 자동 기록
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flight {

    //항공편ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer flightId;

    //항공기ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_Id")
    private Airplane airplane;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<ReservationHistory> reservationHistories;

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

    //가격
    private Integer price;

    //상태
    @Column(length = 1)
    private String state;

    // 생성자
    private Flight(Airplane airplane, String departure, String destination, LocalDateTime outbound, LocalDateTime inbound, String state, int price) {
        this.airplane = airplane;
        this.departure = departure;
        this.destination = destination;
        this.outbound = outbound;
        this.inbound = inbound;
        this.state = state;
        this.price = price;
    }


    // 정적 팩토리 메서드: DTO에서 엔티티로 변환
    public static Flight createFromDto(Airplane airplane, FlightDto flightDto) {
        return new Flight(airplane, flightDto.getDeparture(), flightDto.getDestination(), flightDto.getOutbound(), flightDto.getInbound(), flightDto.getState(), flightDto.getPrice());
    }

    // 필드 업데이트 메서드 (private 접근 수준)
    private void updateDetails(String departure, String destination, LocalDateTime outbound, LocalDateTime inbound, int price) {
        this.departure = departure;
        this.destination = destination;
        this.outbound = outbound;
        this.inbound = inbound;
        this.price = price;
    }

    // 외부에서 접근 가능한 업데이트 메서드
    public void updateFlightDetails(FlightDto flightDto) {
        updateDetails(flightDto.getDeparture(), flightDto.getDestination(), flightDto.getOutbound(), flightDto.getInbound(), flightDto.getPrice());
    }

    public void setState(String state) {
        this.state=state;
    }
}
