package jpabook7.jpashop7.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {

    @NotNull(message = "상품을 선택해주세요")
    private Long itemId;

    @NotNull(message = "수량을 선택해주세요")
    private Integer count;
}
