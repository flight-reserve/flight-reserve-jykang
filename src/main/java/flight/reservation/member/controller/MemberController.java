package flight.reservation.member.controller;

import flight.reservation.member.dto.MemberDto;
import flight.reservation.member.entity.Member;
import flight.reservation.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    
    //회원가입
    @PostMapping("/")
    public ResponseEntity<Member> signup(@RequestBody Member member){
        return new ResponseEntity<>(memberService.saveMember(member), HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Member> signin(@RequestBody MemberDto memberDto){
        return new ResponseEntity<>(memberService.inquiryMember(memberDto),HttpStatus.OK);
    }

}
