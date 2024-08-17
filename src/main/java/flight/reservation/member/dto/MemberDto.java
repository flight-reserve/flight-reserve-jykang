package flight.reservation.member.dto;

import flight.reservation.flight.dto.FlightDto;
import flight.reservation.flight.entity.Flight;
import flight.reservation.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    //아이디
    @NotBlank
    private String memberId;

    //비밀번호
    @NotBlank
    private String pw;

    //이름
    @NotBlank
    private String name;

    //생년월일
    @NotNull // LocalDateTime은 문자열이 아니므로 @NotBlank로 검증불가
    private LocalDateTime birth;

    //이메일
    @NotBlank
    private String email;

    //국적
    @NotBlank
    private String nationality;

    // 정적 팩토리 메서드: 엔티티에서 DTO로 변환
    public static MemberDto fromEntity(Member member) {
        return new MemberDto(
                member.getMemberId(),
                member.getPw(),
                member.getName(),
                member.getBirth(),
                member.getEmail(),
                member.getNationality()

        );
    }


}
