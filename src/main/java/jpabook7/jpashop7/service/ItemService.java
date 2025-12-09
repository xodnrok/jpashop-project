package jpabook7.jpashop7.service;

import jpabook7.jpashop7.controller.BookForm;
import jpabook7.jpashop7.domain.Book;
import jpabook7.jpashop7.domain.Item;
import jpabook7.jpashop7.repository.BookRepository;
import jpabook7.jpashop7.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService{

    private final ItemRepository itemRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("없는 ItemId 입니다."));
    }

    public List<Item> findRecentItems() {
        return itemRepository.findTop3ByOrderByIdDesc();
    }

    public List<Book> findBooks() {
        return bookRepository.findAll();
    }

    public Book findBookOne(Long itemId) {
        return bookRepository.findById(itemId).get();
    }

    //수정
    @Transactional
    public void updateItem(Long itemId, BookForm param) {
        Book book = bookRepository.findById(itemId).get();
        book.setName(param.getName());
        book.setPrice(param.getPrice());
        book.setStockQuantity(param.getStockQuantity());
        book.setAuthor(param.getAuthor());
        book.setIsbn(param.getIsbn());

        //도중추가
        book.setUsedPrice(param.getUsedPrice());
    }

}
