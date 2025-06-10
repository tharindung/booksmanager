package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mybookscollection.BooksManager.entity.Book;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Book Category should not be empty !")
    private String bookCategory;

    //private Set<Book> books = new HashSet<>();
    @JsonBackReference(value="bookCategory")
    private Set<BookDto> books = new HashSet<>();
}
