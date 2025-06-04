package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
