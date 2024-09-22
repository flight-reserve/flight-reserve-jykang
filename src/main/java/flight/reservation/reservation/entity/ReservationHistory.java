package flight.reservation.reservation.entity;

import flight.reservation.flight.entity.Flight;
import flight.reservation.member.entity.Member;
import flight.reservation.reservation.dto.ReservationDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime reservationDate;

    //가격
    private Integer price;

    //항공편ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Flight flight;

    //상태
    @Column(length = 1)
    private String state;

    //생성자
    public ReservationHistory(Member member, Flight flight, LocalDateTime reservationDate, Integer price, String state) {
        this.member=member;
        this.flight=flight;
        this.reservationDate=reservationDate;
        this.price=price;
        this.state=state;
    }

    // 정적 팩토리 메서드: DTO에서 엔티티로 변환 -> 객체타입인 경우는 제외
    public static ReservationHistory createFromDto(Member member, Flight flight, ReservationDto reservationDto) {
        return new ReservationHistory(member,flight,reservationDto.getReservationDate(),reservationDto.getPrice(),reservationDto.getState());
    }


}
