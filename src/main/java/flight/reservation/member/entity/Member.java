package flight.reservation.member.entity;

import flight.reservation.flight.dto.FlightDto;
import flight.reservation.flight.entity.Airplane;
import flight.reservation.flight.entity.Flight;
import flight.reservation.member.dto.MemberDto;
import flight.reservation.reservation.entity.Reservation;
import flight.reservation.reservation.entity.ReservationHistory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    //아이디
    @Id
    @Column(length = 10)
    private String memberId;

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
    private LocalDateTime birth;

    //이메일
    @Column(length = 20)
    private String email;

    //국적
    @Column(length = 20)
    private String nationality;

    // 생성자
    public Member(String memberId, String pw, String name, LocalDateTime birth, String email, String nationality) {
        this.memberId=memberId;
        this.pw=pw;
        this.name=name;
        this.birth=birth;
        this.email=email;
        this.nationality=nationality;
    }

    // 정적 팩토리 메서드: DTO에서 엔티티로 변환
    public static Member createFromDto(MemberDto memberDto) {
        return new Member(memberDto.getMemberId(), memberDto.getPw(), memberDto.getName(), memberDto.getBirth(), memberDto.getEmail(), memberDto.getNationality());
    }

}
