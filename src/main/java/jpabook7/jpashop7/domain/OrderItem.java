package jpabook7.jpashop7.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue
    private Long id;

    private int orderPrice;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //생성메서드
    public static OrderItem createOrderItem(Item item, int count, int orderPrice) {
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item);

        orderItem.setOrderPrice(orderPrice);

        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;
    }



    //order의 주문취소에 대해 재고수량 원복을 위한 cancel
    public void cancel() {
        item.addStock(count);
    }

    //상품 총 가격 반환
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
