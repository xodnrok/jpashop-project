package jpabook7.jpashop7.repository;

import jpabook7.jpashop7.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
