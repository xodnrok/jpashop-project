package jpabook7.jpashop7.service;

import jpabook7.jpashop7.domain.Member;
import jpabook7.jpashop7.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //데이터 읽기만 가능 조금 더 최적화를 위해
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    //회원 가입(회원 가입시 중복 로그인아이디 인 경우 불가)
    @Transactional //데이터 읽기,수정 모두가능
    public Long join(Member member) {

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

    }

    //로그인 체크
    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalStateException("아이디 또는 비밀번호가 맞지 않습니다.."));

        if (!member.getPassword().equals(password)) {
            throw new IllegalStateException("아이디 또는 비밀번호가 맞지 않습니다.");
        }
        return member;
    }

    //아이디로 멤버찾기 단건조회
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("멤버아이디로 회원을 못찾았습니다."));
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    //회원 로그인아이디 중복 검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findByLoginId(member.getLoginId()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 로그인아이디입니다.");});
    }

    //api연습 메서드 수정
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);

    }
}
