package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookRequestDto;

import java.util.List;

public interface BookRequestService {

    BookRequestDto createBookRequest(BookRequestDto bookRequestDto);

    List<BookRequestDto> getAllBookRequests();

    BookRequestDto getBookRequestById(Long bookRequestId);

    BookRequestDto updateBookRequest(Long bookRequestId, BookRequestDto bookRequestDto);

    void deleteBookRequest(Long bookRequestId);
}
