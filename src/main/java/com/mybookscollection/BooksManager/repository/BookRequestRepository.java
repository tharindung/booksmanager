package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
}
