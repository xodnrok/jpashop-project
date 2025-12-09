package jpabook7.jpashop7.repository;

import jpabook7.jpashop7.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String itemName; //아이템 이름
    private OrderStatus orderStatus;//주문 상태
}
