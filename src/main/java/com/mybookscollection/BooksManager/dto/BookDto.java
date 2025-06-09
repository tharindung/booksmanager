package com.mybookscollection.BooksManager.dto;

import com.mybookscollection.BooksManager.entity.BookCategory;
import com.mybookscollection.BooksManager.entity.BookCondition;
import com.mybookscollection.BooksManager.entity.BookRequest;
import com.mybookscollection.BooksManager.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty(message = "Book Name should not be empty !")
    private String bookName;

    private byte[] bookImg;

    @NotEmpty(message = "Author should not be empty !")
    private String bookAuthor;

    private LocalDate bookPurchaseDate;

    @NotNull(message = "Book Category is required !")
    private BookCategory bookCategory;

    @NotNull(message = "Book Condition is required !")
    private BookCondition bookCondition;

    private String bookCatalogNo;

    private String bookNotes;

    @NotNull(message = "Book Shareable is required !") //Validation only works on Boolean (not boolean)
    //private boolean isBookShareable;
    private Boolean bookShareable;

    @NotNull(message = "Book Owner is required !")
    private User bookOwner;

    private Set<BookRequest> bookRequests = new HashSet<>();
}
