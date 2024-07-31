package flight.reservation.member.dto;

import lombok.*;

import java.time.LocalDateTime;



@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    //아이디
    private String memberId;

    //비밀번호
    private String pw;

    //이름
    private String name;

    //생년월일
    private LocalDateTime birth;

    //이메일
    private String email;

    //국적
    private String nationality;

}
