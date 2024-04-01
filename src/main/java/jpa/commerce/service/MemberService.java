package jpa.commerce.service;

import jpa.commerce.domain.Member;
import jpa.commerce.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // @Autowired보다 이 어노테이션을 통한 생성자 주입이 더 효율적이다.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long registMember(Member member) {

        isDuplicateMember(member);
        memberRepository.regist(member);
        return member.getId();
    }

    private void isDuplicateMember(Member member) {
        List<Member> findMemberNames = memberRepository.findMemberByName(member.getName());
        if (!findMemberNames.isEmpty()) { // 있는지 없는지 판단 로직은 리포지토리 sql 쿼리문에 있다.
            throw new IllegalStateException("중복 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findAllMembers() {
        return memberRepository.findAllMembers();
    }

    /**
     * 개별 회원 조회 - id값 사용
     */
    public Member findMemberById(Long memberId) {
        return memberRepository.findMemberById(memberId);
    }

}
