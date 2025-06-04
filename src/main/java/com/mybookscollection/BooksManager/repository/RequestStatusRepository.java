package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {
}
