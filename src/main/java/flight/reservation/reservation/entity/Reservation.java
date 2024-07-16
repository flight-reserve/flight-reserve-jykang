package flight.reservation.reservation.entity;

import flight.reservation.filght.entity.Flight;
import flight.reservation.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicInsert
public class Reservation {
    //예약아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationId;

    //아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //항공편ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Flight flight;

    //예약날짜
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationDate;

    //가격
    private int price;

    //상태
    @Column(length = 1)
    @ColumnDefault("'Y'")
    private String state;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationHistory> reservationHistories;
}
