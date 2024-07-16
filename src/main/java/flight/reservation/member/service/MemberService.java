package flight.reservation.member.service;

import flight.reservation.member.dto.MemberDto;
import flight.reservation.member.entity.Member;
import flight.reservation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    //로그인
    public Member inquiryMember(MemberDto memberDto) {
        return memberRepository.findByMemberIdAndPw(memberDto.getMemberId(), memberDto.getPw());
    }


}
