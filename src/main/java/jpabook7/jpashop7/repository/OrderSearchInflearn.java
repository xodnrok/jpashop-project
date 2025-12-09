package jpabook7.jpashop7.repository;

import jpabook7.jpashop7.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchInflearn {
    private String memberName;
    private OrderStatus orderStatus;
}
