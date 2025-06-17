package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookConditionRepository extends JpaRepository<BookCondition, Long> {
}
