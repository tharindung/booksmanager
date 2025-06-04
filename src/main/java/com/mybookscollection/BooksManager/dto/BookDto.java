package com.mybookscollection.BooksManager.dto;

import com.mybookscollection.BooksManager.entity.BookCategory;
import com.mybookscollection.BooksManager.entity.BookCondition;
import com.mybookscollection.BooksManager.entity.BookRequest;
import com.mybookscollection.BooksManager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long bookId;

    private String bookName;

    private byte[] bookImg;

    private String bookAuthor;

    private LocalDate bookPurchaseDate;

    private BookCategory bookCategory;

    private BookCondition bookCondition;

    private String bookCatalogNo;

    private String bookNotes;

    private boolean isBookShareable;

    private User bookOwner;

    private Set<BookRequest> bookRequests = new HashSet<>();
}
