package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto);

    List<BookDto> getAllBooks();

    BookDto getBookById(Long bookId);

    BookDto updateBook(Long bookId, BookDto bookDto);

    void deleteBook(Long bookId);
}
