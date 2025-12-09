package jpabook7.jpashop7.service;

import jpabook7.jpashop7.domain.*;
import jpabook7.jpashop7.dto.OrderQueryDto;
import jpabook7.jpashop7.repository.ItemRepository;
import jpabook7.jpashop7.repository.MemberRepository;
import jpabook7.jpashop7.repository.OrderRepository;
import jpabook7.jpashop7.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //pk로 엔티티 조회
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("없는 상품(아이템)입니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("없는 회원(멤버)입니다."));

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, count, item.getPrice());

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }


    //주문취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("찾을 수 없는 주문입니다."));
        order.cancel();
    }

    //검색
    public List<OrderQueryDto> findAll(OrderSearch orderSearch, Long memberId) {
        return orderRepository.findAll(orderSearch, memberId);
    }
}
