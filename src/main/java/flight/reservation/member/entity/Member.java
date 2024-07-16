package flight.reservation.member.entity;

import flight.reservation.reservation.entity.Reservation;
import flight.reservation.reservation.entity.ReservationHistory;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Member {

    //아이디
    @Id
    @Column(length = 10)
    private int memberId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ReservationHistory> reservationHistories;

    //비밀번호
    @Column(length = 10)
    private String pw;

    //이름
    @Column(length = 10)
    private String name;

    //생년월일
    private LocalDate birth;

    //이메일
    @Column(length = 20)
    private String email;

    //국적
    @Column(length = 20)
    private String nationality;
}
