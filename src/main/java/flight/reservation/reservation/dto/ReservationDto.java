package flight.reservation.reservation.dto;

import flight.reservation.flight.entity.Flight;
import flight.reservation.member.entity.Member;
import flight.reservation.reservation.entity.Reservation;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    //예약 아이디
    private Integer reservationId;

    //멤버 아이디
    private String memberId;

    //항공편 ID
    private Integer flightId;

    //예약 날짜
    private LocalDateTime reservationDate;

    //가격
    private Integer price;

    //상태
    private String state;


    // 정적 팩토리 메서드: 엔티티에서 DTO로 변환
    public static ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getReservationId(),
                reservation.getMember().getMemberId(),
                reservation.getFlight().getFlightId(),
                reservation.getReservationDate(),
                reservation.getPrice(),
                reservation.getState()
        );
    }


}