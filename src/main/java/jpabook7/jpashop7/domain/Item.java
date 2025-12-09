package jpabook7.jpashop7.domain;

import jakarta.persistence.*;
import jpabook7.jpashop7.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @Version
    private Long version;

    //수량 증가
    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

    //수량 감소(0아래는 불가하므로 추가 검증 로직)
    public void removeStock(int quantity) {
        int restStock = stockQuantity - quantity;

        if (restStock < 0) {
            throw new NotEnoughStockException("수량 더 감소 불가능"); //조금더 명확한 에러 만듬
        }

        stockQuantity -= quantity;
    }
}
