package jpabook7.jpashop7.api;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jpabook7.jpashop7.domain.*;
import jpabook7.jpashop7.repository.OrderRepository;
import jpabook7.jpashop7.repository.OrderSearchInflearn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public Result<List<OrderDto>> ordersV1() {
        List<OrderDto> list = orderRepository.findAllInflearn(new OrderSearchInflearn()).stream()
                .map(OrderDto::new)
                .toList();

        return new Result<>(list.size(), list);
    }



    @Data
    @AllArgsConstructor
    static class Result<T> {

        private int count;
        private T data;

    }

    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private List<OrderItemDto> orderItem;
        private Address address;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private int totalPrice;

        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderItem = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .toList();
            this.address = order.getDelivery().getAddress();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.totalPrice = order.getTotalPrice();
        }
    }

    @Data
    static class OrderItemDto {
        private Long orderItemId;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            this.orderItemId = orderItem.getId();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
