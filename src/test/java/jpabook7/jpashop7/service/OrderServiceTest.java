package jpabook7.jpashop7.service;

import jakarta.persistence.EntityManager;
import jpabook7.jpashop7.domain.*;
import jpabook7.jpashop7.dto.OrderQueryDto;
import jpabook7.jpashop7.exception.NotEnoughStockException;
import jpabook7.jpashop7.repository.OrderRepository;
import jpabook7.jpashop7.repository.OrderSearch;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    @Test
//    @Rollback(value = false)
    public void 상품주문() throws Exception {

        //given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order getOrder = orderRepository.findById(orderId).get();

        //then

        // 주문생성 id와 저장소에서 id로 찾은 주문 id가 같은지 비교
        Assertions.assertThat(orderId).isEqualTo(getOrder.getId());

        //주문 생성하고 주문 상태가 ORDER 로 되어 있는지 검증
        Assertions.assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);

        //주문 생성하고 배송상태가 READY 로 되어 있는지 검증
        Assertions.assertThat(getOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);

        //만원짜리 2개 주문했을때 총 가격이 2만원이 맞는지 검증
        Assertions.assertThat(getOrder.getTotalPrice()).isEqualTo(20000);

        //10개에서 2개주문하면 재고수량이 2개 감소한 8개가 되어야 한다.
        Assertions.assertThat(book.getStockQuantity()).isEqualTo(8);

        //한개 주문했을때 해당 orderItem 리스트에 저장되어 있는지
        Assertions.assertThat(getOrder.getOrderItems().size()).isEqualTo(1);

    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {

        //given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);
        int orderCount = 11;

        //when

        try {
            orderService.order(member.getId(), book.getId(), orderCount);
        } catch (NotEnoughStockException e) {
            return;
        }

        //then
        fail("여기 까지 오면 안됨");


    }

    @Test
    public void 주문취소() throws Exception {

        //given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);
        Order getOrder = orderRepository.findById(orderId).get();

        //then

        //생성된 주문을 취소 할경우 상태가 CANCEL 로 바뀌는지 검증
        Assertions.assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);

        //주문취소시 2개 주문한게 다시 원복되어서 책 재고수량이 10개로 다시 바뀌어야함
        Assertions.assertThat(book.getStockQuantity()).isEqualTo(10);

    }

    @Test
//    @Rollback(value = false)
    public void 회원_주문목록_찾기() throws Exception {

        //given

        //회원1
        Member member1 = createMember();
        Book book1 = createBook("JPA", 10000, 10);
        Book book3 = createBook("ORM", 30000, 30);
        int orderCount1 = 2;
        int orderCount3 = 10;
        orderService.order(member1.getId(), book1.getId(), orderCount1);
        orderService.order(member1.getId(), book3.getId(), orderCount3);

        //회원2
        Member member2 = new Member();
        member2.setName("회원2");
        member2.setAddress(new Address("부산", "해운대", "321-321"));
        em.persist(member2);
        Book book2 = createBook("MYSQL", 20000, 20);
        int orderCount2 = 5;
        orderService.order(member2.getId(), book2.getId(), orderCount2);


        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setItemName("JPA");

        //when
        List<OrderQueryDto> findorderList = orderRepository.findAll(orderSearch, member1.getId());

        //해당 주문 dto 를 리스트에서 꺼내온다.
        OrderQueryDto findOrderDto = findorderList.get(0);

        //then

        //2개를 저장시 로그인회원 하나의 데이터만 있는지
        Assertions.assertThat(findorderList.size()).isEqualTo(1);

        //주문목록 찾았을때 회원이름이 로그인한 회원이 맞는지
        Assertions.assertThat(findOrderDto.getMemberName()).isEqualTo("회원1");

        //로그인한 회원이 책 주문을 몇개 했는지
        Assertions.assertThat(findOrderDto.getCount()).isEqualTo(2);

        //로그인된 회원이 주문한 아이템 이름이 맞는지
        Assertions.assertThat(findOrderDto.getItemName()).isEqualTo("JPA");

        //로그인된 회원이 주문의 상태가 맞는지
        Assertions.assertThat(findOrderDto.getOrderStatus()).isEqualTo(OrderStatus.ORDER);

    }


    //Book 클래스 생성메서드
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    //Member 클래스 생성메서드
    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }


}