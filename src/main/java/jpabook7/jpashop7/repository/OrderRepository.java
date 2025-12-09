package jpabook7.jpashop7.repository;

import jpabook7.jpashop7.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

}
