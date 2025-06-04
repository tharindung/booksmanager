package com.mybookscollection.BooksManager.dto;

import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.entity.BookRequest;
import com.mybookscollection.BooksManager.entity.Country;
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
public class UserDto {

    private Long userId;

    private String userName;

    private Country userCountry;

    private String userEmail;

    private String userPassword;

    private LocalDate userJoinedDate;

    private Set<Book> books = new HashSet<>();

    private Set<BookRequest> bookRequests = new HashSet<>();
}
