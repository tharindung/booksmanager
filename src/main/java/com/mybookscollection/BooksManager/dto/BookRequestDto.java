package com.mybookscollection.BooksManager.dto;

import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.entity.RequestStatus;
import com.mybookscollection.BooksManager.entity.User;
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

    private Book book;

    private User requestedUser;

    private RequestStatus requestStatus;
}
