package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
}
