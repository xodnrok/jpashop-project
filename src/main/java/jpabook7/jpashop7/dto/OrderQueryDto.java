package jpabook7.jpashop7.dto;

import com.querydsl.core.annotations.QueryProjection;
import jpabook7.jpashop7.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderQueryDto {
    private Long id;
    private String memberName;
    private String itemName;
    private int orderPrice;
    private int count;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    @QueryProjection
    public OrderQueryDto(Long orderId, String memberName, String itemName, int orderPrice, int count, OrderStatus orderStatus, LocalDateTime orderDate) {
        this.id = orderId;
        this.memberName = memberName;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }
}
