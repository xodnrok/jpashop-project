package jpabook7.jpashop7.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jpabook7.jpashop7.domain.Book;
import jpabook7.jpashop7.domain.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {

    private Long id; //상품 수정시 id 값이 필요

    @NotBlank(message = "이름을 입력하세요")
    private String name;

    @NotNull(message = "가격을 입력하세요")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Integer price;

    @NotNull(message = "수량을 입력하세요")
    @Min(value = 0, message = "수량은 0개 이상이어야 합니다.")
    private Integer stockQuantity;

    @NotBlank(message = "저자를 입력하세요")
    private String author;

    @NotBlank(message = "ISBN을 입력하세요")
    private String isbn;

    //도중추가
    private Integer usedPrice;

    public BookForm() {
    }

    public BookForm(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();

        //도중추가
        this.usedPrice = book.getUsedPrice();
    }



}
