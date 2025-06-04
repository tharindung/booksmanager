package com.mybookscollection.BooksManager.dto;

import com.mybookscollection.BooksManager.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDto {

    private Long bookCategoryId;
    private String bookCategory;
    private Set<Book> books = new HashSet<>();
}
