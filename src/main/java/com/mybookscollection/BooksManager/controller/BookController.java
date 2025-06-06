package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.BookDto;
import com.mybookscollection.BooksManager.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/books")
public class BookController {

    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto)
    {
        BookDto savedBook = bookService.createBook(bookDto);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks()
    {
        List<BookDto> allBooks = bookService.getAllBooks();

        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long bookId)
    {
        BookDto foundBook = bookService.getBookById(bookId);

        return new ResponseEntity<>(foundBook, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long bookId, @RequestBody BookDto bookDto)
    {
        BookDto updatedBook = bookService.updateBook(bookId, bookDto);

        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId)
    {
        bookService.deleteBook(bookId);

        return new ResponseEntity<>("Book with ID : " + bookId + " deleted successfully !", HttpStatus.OK);
    }
}
