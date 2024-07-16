package flight.reservation.member.repository;

import flight.reservation.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Integer> {

    Optional<Member> findByMemberIdAndPw(int memberId, String pw);

    Optional<Member> findByMemberId(int memberId);
}
