package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
