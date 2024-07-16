package flight.reservation.filght.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class FlightHistory {

    //항공이력ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int flightHistoryId;

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
    private LocalDate outbound;

    //오는편
    private LocalDate inbound;

    //생성일자
    private LocalDate createdDate;

    //변경일자
    private LocalDate updatedDate;
    //가격
    private int price;

    //상태
    @Column(length = 1)
    private String state;

}
