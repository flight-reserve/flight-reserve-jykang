package flight.reservation.reservation.service;


import flight.reservation.flight.entity.Flight;
import flight.reservation.flight.repository.FlightRepository;
import flight.reservation.member.entity.Member;
import flight.reservation.member.repository.MemberRepository;
import flight.reservation.reservation.dto.ReservationDto;
import flight.reservation.reservation.entity.Reservation;
import flight.reservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    private final FlightRepository flightRepository;

    //특정 ID에 대한 예약 조회
    public List<ReservationDto> inquiryReservation(String memberId){
        List<Reservation> findAllReservation= reservationRepository.findByMember_MemberId(memberId);
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for(Reservation reservation : findAllReservation){
            ReservationDto reservationDto = ReservationDto.toDto(reservation);
            reservationDtoList.add(reservationDto);
        }
        return reservationDtoList;
    }

    //예약 추가
    public void saveReservation(ReservationDto reservationDto) {
        Member member = memberRepository.findByMemberId(reservationDto.getMemberId());
        //MemberDto memberDto = MemberDto.fromEntity(member); //entity to dto

        Flight flight = flightRepository.findByFlightId(reservationDto.getReservationId());
        //FlightDto flightDto = FlightDto.fromEntity(flight);

        Reservation reservation = Reservation.createFromDto(member,flight);
        reservationRepository.save(reservation);
    }

    //예약 취소 - 상태값 변경
    public void changeReservation(int reservationId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        reservation.setState("N");
        reservationRepository.save(reservation);
    }

    //특정 날짜 예약 조회
    public List<ReservationDto> inquirySpecificReservation(LocalDateTime reservationData) {
        List<Reservation> SpecificReservation = reservationRepository.findByReservationDate(reservationData);
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for(Reservation reservation : SpecificReservation){
//            ReservationDto reservationDto = new ReservationDto();
//            reservationDto.setMemberId(reservation.getMember().getMemberId());
//            reservationDto.setFlightId(reservation.getFlight().getFlightId());
//            reservationDto.setReservationDate(reservation.getReservationDate());
//            reservationDto.setPrice(reservation.getPrice());
//            reservationDto.setState(reservation.getState());
            ReservationDto reservationDto = ReservationDto.toDto(reservation);
            reservationDtoList.add(reservationDto);
        }
        return reservationDtoList;
    }
}
