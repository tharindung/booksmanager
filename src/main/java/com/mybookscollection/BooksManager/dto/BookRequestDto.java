package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.entity.RequestStatus;
import com.mybookscollection.BooksManager.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {

    private Long bookRequestId;

    @NotNull(message = "Book is required !")
    //private Book book;
    @JsonManagedReference(value="reqBook")
    private BookDto book;

    @NotNull(message = "Requested User is required !")
    //private User requestedUser;
    @JsonManagedReference(value="reqUser")
    private UserDto requestedUser;

    @NotNull(message = "Request Status is required !")
    //private RequestStatus requestStatus;
    @JsonManagedReference(value="reqStatus")
    private RequestStatusDto requestStatus;
}
