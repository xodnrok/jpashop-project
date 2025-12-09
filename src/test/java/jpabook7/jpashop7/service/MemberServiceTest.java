package jpabook7.jpashop7.service;

import jpabook7.jpashop7.domain.Member;
import jpabook7.jpashop7.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    //회원 가입 성공

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception {

        //given (이게 주어졌을때)

        Member member = new Member();
        member.setName("MemberA");
        member.setLoginId("test1234");
        member.setPassword("1234test");

        //when  (이행동을 하면)

        Long saveId = memberService.join(member);
        Member findMember = memberService.findOne(saveId);

        //then  (이 결과가 나올것이다.)

        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
    }

    //중복 회원 검증 로직 제대로 동작하나
    @Test
    public void 중복_회원_예외() throws Exception{

        //given(이게 주어졌을때)
        Member member1 = new Member();
        member1.setName("Kim1");
        member1.setLoginId("test1234");
        member1.setPassword("1234test");

        Member member2 = new Member();
        member2.setName("Kim2");
        member2.setLoginId("test1234");
        member2.setPassword("1234test");

        //when(이 행동을 하면)
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

    //로그인 제대로 동작하나
    @Test
    public void 로그인()throws Exception {

        //given(이게 주어졌을때)
        Member member1 = new Member();
        member1.setName("Kim1");
        member1.setLoginId("test1234");
        member1.setPassword("1234test");

        memberService.join(member1);

        //when
        Member loginMember = memberService.login(member1.getLoginId(), member1.getPassword());

        //then
        Assertions.assertThat(loginMember.getId()).isEqualTo(member1.getId());
    }

    //로그인 실패 테스트
    @Test
    public void 로그인_실패_예외_비밀번호() throws Exception {
        //given(이게 주어졌을때)
        Member member1 = new Member();
        member1.setName("Kim1");
        member1.setLoginId("test1234");
        member1.setPassword("1234test");

        memberService.join(member1);


        //when,then
        assertThrows(IllegalStateException.class, () -> memberService.login(member1.getLoginId(), "1234"));


    }

    //로그인 실패 테스트
    @Test
    public void 로그인_실패_예외_아이디() throws Exception {

        //given,when,then
        assertThrows(IllegalStateException.class, () -> memberService.login("test", "1234"));

    }
}