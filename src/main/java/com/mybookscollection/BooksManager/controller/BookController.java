package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.BookDto;
import com.mybookscollection.BooksManager.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/books")
@Tag(name = "Books", description = "CRUD operations for managing books")
public class BookController {

    private BookService bookService;

    @Operation(
            summary = "Create a new book",
            description = "Adds a new book to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Book created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto)
    {
        BookDto savedBook = bookService.createBook(bookDto);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all books",
            description = "Retrieves a list of all books",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of books retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks()
    {
        List<BookDto> allBooks = bookService.getAllBooks();

        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @Operation(
            summary = "Get book by ID",
            description = "Retrieves a book by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long bookId)
    {
        BookDto foundBook = bookService.getBookById(bookId);

        return new ResponseEntity<>(foundBook, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a book",
            description = "Updates an existing book by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long bookId, @RequestBody @Valid BookDto bookDto)
    {
        BookDto updatedBook = bookService.updateBook(bookId, bookDto);

        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a book",
            description = "Deletes a book by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId)
    {
        bookService.deleteBook(bookId);

        return new ResponseEntity<>("Book with ID : " + bookId + " deleted successfully !", HttpStatus.OK);
    }
}
