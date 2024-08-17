package flight.reservation.reservation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import flight.reservation.flight.entity.Flight;
import flight.reservation.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    //예약아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reservationId;

    //아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Id")
    //@JsonIgnore
    private Member member;

    //항공편ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_Id")
    //@JsonIgnore
    private Flight flight;

    //예약날짜
    private LocalDateTime reservationDate;

    //가격
    private Integer price;

    //상태
    @Column(length = 1)
    @ColumnDefault("'Y'")
    private String state;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReservationHistory> reservationHistories;

    // 생성자
    public Reservation(Member memberId, Flight flightId) {
        this.member = memberId;
        this.flight = flightId;


    }

    // 정적 팩토리 메서드: DTO에서 엔티티로 변환 -> 객체타입인 경우는 제외
    public static Reservation createFromDto(Member member, Flight flight) {
        return new Reservation(member,flight);
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setMember(Member member) {
        this.member=member;
    }

    public void setFlight(Flight flight) {
        this.flight=flight;
    }
}
