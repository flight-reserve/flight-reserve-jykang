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
    public MemberDto saveMember(MemberDto memberDto) {

        Member member = Member.createFromDto(memberDto);
        memberRepository.save(member);
        return new MemberDto(member.getMemberId(), member.getPw(), member.getName(),member.getBirth(),member.getEmail(),member.getNationality());
    }

    //로그인
    public void inquiryMember(MemberDto memberDto) {
        memberRepository.findByMemberIdAndPw(memberDto.getMemberId(), memberDto.getPw());
    }

}
