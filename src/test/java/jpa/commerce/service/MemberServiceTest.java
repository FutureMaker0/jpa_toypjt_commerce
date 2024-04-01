package jpa.commerce.service;

import jpa.commerce.domain.Member;
import jpa.commerce.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
    public void 테스트_회원가입() {
        //given
        Member member = new Member();
        member.setName("testName1");

        //when
        Long registedId = memberService.registMember(member);

        //then
        //assertThat(registedId).isEqualTo(member.getId());
        assertEquals(member.getId(), registedId);
    }

    @Test
    public void 테스트_중복회원_검증() {
        //given
        Member member1 = new Member();
        member1.setName("testName1");
        Member member2 = new Member();
        member2.setName("testName2");

        //when
        memberService.registMember(member1);
        memberService.registMember(member2);

        //then
        fail("테스트 검증에 실패하고 예외가 발생해야 한다. 아직 커스텀 예외 정의하지 않음.");
    }

}