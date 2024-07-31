package flight.reservation.member.repository;

import flight.reservation.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member,Integer> {

    Member findByMemberIdAndPw(String memberId, String pw);

    Member findByMemberId(String memberId);

}
