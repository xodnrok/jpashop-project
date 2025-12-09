package jpabook7.jpashop7.domain;

import jakarta.persistence.Entity;
import jpabook7.jpashop7.controller.BookForm;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book extends Item{

    private String author;
    private String isbn;

    //도중 추가
    private Integer usedPrice;

    public static Book createBook(BookForm form) {

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        //도중추가
        book.setUsedPrice(form.getUsedPrice());

        return book;

    }
}
