package flight.reservation.member.controller;

import flight.reservation.member.dto.MemberDto;
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
    @PostMapping
    public ResponseEntity<MemberDto> signup(@RequestBody MemberDto memberDto){
        return new ResponseEntity<>(memberService.saveMember(memberDto), HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> signin(@RequestBody MemberDto memberDto){
        memberService.inquiryMember(memberDto);
        return new ResponseEntity<>("로그인에 성공하였습니다",HttpStatus.OK);
    }

}
