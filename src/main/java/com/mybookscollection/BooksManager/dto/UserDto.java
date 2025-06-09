package com.mybookscollection.BooksManager.dto;

import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.entity.BookRequest;
import com.mybookscollection.BooksManager.entity.Country;
import jakarta.validation.constraints.Email;
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
public class UserDto {

    private Long userId;

    @NotEmpty(message = "Name should not be empty !")
    private String userName;

    @NotNull(message = "Country is required !")
    private Country userCountry;

    @Email(message = "Email should be valid !")
    @NotEmpty(message = "Email should not be empty !")
    private String userEmail;

    @NotEmpty(message = "Password should not be empty !")
    private String userPassword;

    @NotNull(message = "User Joined Date is required !")
    private LocalDate userJoinedDate;

    private Set<Book> books = new HashSet<>();

    private Set<BookRequest> bookRequests = new HashSet<>();
}
