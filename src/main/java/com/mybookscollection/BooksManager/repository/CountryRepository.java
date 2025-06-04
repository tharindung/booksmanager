package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
