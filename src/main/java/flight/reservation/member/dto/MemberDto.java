package flight.reservation.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MemberDto {

    //아이디
    int memberId;

    //비밀번호
    String pw;



}
