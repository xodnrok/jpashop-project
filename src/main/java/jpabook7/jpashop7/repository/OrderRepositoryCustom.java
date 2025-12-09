package jpabook7.jpashop7.repository;

import jpabook7.jpashop7.domain.Order;
import jpabook7.jpashop7.dto.OrderQueryDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderQueryDto> findAll(OrderSearch orderSearch, Long memberId);

    List<Order> findAllInflearn(OrderSearchInflearn orderSearchInflearn);
}
