package jpabook7.jpashop7.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook7.jpashop7.domain.*;
import jpabook7.jpashop7.dto.OrderQueryDto;
import jpabook7.jpashop7.dto.QOrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static jpabook7.jpashop7.domain.QDelivery.*;
import static jpabook7.jpashop7.domain.QItem.*;
import static jpabook7.jpashop7.domain.QMember.*;
import static jpabook7.jpashop7.domain.QOrder.*;
import static jpabook7.jpashop7.domain.QOrderItem.*;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<OrderQueryDto> findAll(OrderSearch orderSearch, Long memberId) {
        return queryFactory
                .select(new QOrderQueryDto(
                        order.id,
                        member.name,
                        item.name,
                        orderItem.orderPrice,
                        orderItem.count,
                        order.status,
                        order.orderDate
                        )
                )
                .from(order)
                .join(order.member, member)
                .join(order.orderItems, orderItem)
                .join(orderItem.item, item)
                .where(itemNameEq(orderSearch.getItemName()),
                        statusEq(orderSearch.getOrderStatus()),
                        order.member.id.eq(memberId))
                .fetch();


    }

    private Predicate statusEq(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return order.status.eq(orderStatus);
    }

    private Predicate itemNameEq(String itemName) {
        if (!StringUtils.hasText(itemName)) {
            return null;
        }
        return item.name.contains(itemName);
    }


    //인프런 공부용 메서드
    @Override
    public List<Order> findAllInflearn(OrderSearchInflearn orderSearchInflearn) {
        return queryFactory
                .selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery,delivery).fetchJoin()
                .where(statusEqInflearn(orderSearchInflearn.getOrderStatus()),
                        nameLike(orderSearchInflearn.getMemberName()))
                .limit(1000)
                .fetch();
    }
    //인프런 공부용 메서드
    private Predicate nameLike(String memberName) {
        if (!StringUtils.hasText(memberName)) {
            return null;
        }
        return member.name.like(memberName);
    }
    //인프런 공부용 메서드
    private Predicate statusEqInflearn(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return order.status.eq(orderStatus);
    }

}
