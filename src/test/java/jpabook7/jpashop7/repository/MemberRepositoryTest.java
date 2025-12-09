package jpabook7.jpashop7.repository;

import jakarta.persistence.EntityManager;
import jpabook7.jpashop7.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
////    @Rollback(value = false) //DB에 실제로 저장되는지 확인하기 위해서
//    @Transactional
//    public void testMember() {
//
//        Member member = new Member(); //멤버 생성
//        member.setName("MemberA");
//        Member saveMember = memberRepository.save(member); //멤버 저장
//        Member findMember = memberRepository.findById(saveMember.getId()).get(); //멤버 아이디로 저장소에서 찾기
//
//        Assertions.assertThat(findMember).isEqualTo(member); //동일한 객체인지
//        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());//동일한 이름인지
//    }
}