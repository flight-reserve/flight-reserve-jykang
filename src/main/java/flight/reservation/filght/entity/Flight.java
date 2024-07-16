package flight.reservation.filght.entity;

import flight.reservation.reservation.entity.Reservation;
import flight.reservation.reservation.entity.ReservationHistory;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Flight {

    //항공편ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int flightId;

    //항공기ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplaneId")
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
