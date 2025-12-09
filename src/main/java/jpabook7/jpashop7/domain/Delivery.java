package jpabook7.jpashop7.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @Column(name = "delivery_id")
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
