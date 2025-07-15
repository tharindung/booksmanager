package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.BookRequestDto;
import com.mybookscollection.BooksManager.service.BookRequestService;
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
@RequestMapping("booksman/api/book-requests")
@Tag(name = "Book Requests", description = "CRUD operations for managing book requests")
public class BookRequestController {

    private BookRequestService bookRequestService;

    @Operation(
            summary = "Create a new book request",
            description = "Creates a new book request and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Book request created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookRequestDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<BookRequestDto> createBookRequest(@RequestBody @Valid BookRequestDto bookRequestDto)
    {
        BookRequestDto savedBookRequest = bookRequestService.createBookRequest(bookRequestDto);

        return new ResponseEntity<>(savedBookRequest, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all book requests",
            description = "Retrieves a list of all book requests",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of book requests",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookRequestDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<BookRequestDto>> getAllBookRequests()
    {
        List<BookRequestDto> allBookRequests = bookRequestService.getAllBookRequests();

        return new ResponseEntity<>(allBookRequests, HttpStatus.OK);
    }

    @Operation(
            summary = "Get book request by ID",
            description = "Retrieves a single book request by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book request found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookRequestDto.class))),
                    @ApiResponse(responseCode = "404", description = "Book request not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<BookRequestDto> getBookRequestById(@PathVariable("id") Long bookRequestId)
    {
        BookRequestDto foundBookRequest = bookRequestService.getBookRequestById(bookRequestId);

        return new ResponseEntity<>(foundBookRequest, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a book request",
            description = "Updates an existing book request by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book request updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookRequestDto.class))),
                    @ApiResponse(responseCode = "404", description = "Book request not found", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<BookRequestDto> updateBookRequest(@PathVariable("id") Long bookRequestId, @RequestBody @Valid BookRequestDto bookRequestDto)
    {
        BookRequestDto updatedBookRequest = bookRequestService.updateBookRequest(bookRequestId, bookRequestDto);

        return new ResponseEntity<>(updatedBookRequest, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a book request",
            description = "Deletes a book request by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book request deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Book request not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBookRequest(@PathVariable("id") Long bookRequestId)
    {
        bookRequestService.deleteBookRequest(bookRequestId);

        return new ResponseEntity<>("Book Request with ID : " + bookRequestId + " deleted successfully !", HttpStatus.OK);
    }
}
