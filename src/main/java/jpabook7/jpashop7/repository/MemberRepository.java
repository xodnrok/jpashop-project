package jpabook7.jpashop7.repository;

import jpabook7.jpashop7.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);

    Optional<Member> findByLoginId(String loginId);
}
