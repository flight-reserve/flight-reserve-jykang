package flight.reservation.member.controller;

import flight.reservation.member.dto.MemberDto;
import flight.reservation.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Validated //@pathvariable 적용된 변수에 대한 유효성 검증도 하기위함
public class MemberController {

    private final MemberService memberService;
    
    //회원가입
    @PostMapping
    public ResponseEntity<MemberDto> signup(@RequestBody @Valid MemberDto memberDto){
        return new ResponseEntity<>(memberService.saveMember(memberDto), HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> signin(@RequestBody @Valid MemberDto memberDto){
        memberService.inquiryMember(memberDto);
        return new ResponseEntity<>("로그인에 성공하였습니다",HttpStatus.OK);
    }

}
