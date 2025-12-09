package jpabook7.jpashop7.repository;

import jpabook7.jpashop7.domain.Item;
import jpabook7.jpashop7.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    //최근상품 가져오기
    List<Item> findTop3ByOrderByIdDesc();


}
