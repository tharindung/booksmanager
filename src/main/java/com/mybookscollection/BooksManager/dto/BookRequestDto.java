package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookRequestId")
public class BookRequestDto {

    private Long bookRequestId;

    @NotNull(message = "Book is required !")
    //@JsonManagedReference(value="reqBook")
    private BookDto book;

    //@NotNull(message = "Requested User is required !")
    //@JsonManagedReference(value="reqUser")
    //private UserDto requestedUser;

    /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    @NotNull(message = "Requested User is required !")
    //@JsonManagedReference(value="reqUser")
    private UserDisplayDto requestedUser;

    @NotNull(message = "Request Status is required !")
    //@JsonManagedReference(value="reqStatus")
    private RequestStatusDto requestStatus;
}
