package flight.reservation.reservation.entity;

import flight.reservation.flight.entity.Flight;
import flight.reservation.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class ReservationHistory {

    //예약아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationHistoryId;

    //아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    //예약아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Reservation reservation;


    //예약날짜
    private LocalDate reservationDate;

    //가격
    private Integer price;

    //항공편ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Flight flight;

    //상태
    @Column(length = 1)
    private String state;

}
