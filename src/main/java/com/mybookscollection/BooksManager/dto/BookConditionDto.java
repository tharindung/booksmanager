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
public class BookConditionDto {

    private Long bookConditionId;
    private String bookCondition;
    private Set<Book> books = new HashSet<>();
 }
