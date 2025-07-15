package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.BookConditionDto;
import com.mybookscollection.BooksManager.service.BookConditionService;
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
@RequestMapping("booksman/api/book-conditions")
@Tag(name = "Book Conditions", description = "CRUD operations for book conditions")
public class BookConditionController {

    private BookConditionService bookConditionService;

    @Operation(
            summary = "Create a new book condition",
            description = "Creates a new book condition and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Book condition created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookConditionDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<BookConditionDto> createBookCondition(@RequestBody @Valid BookConditionDto bookConditionDto)
    {
        BookConditionDto savedBookCondition = bookConditionService.createBookCondition(bookConditionDto);

        return new ResponseEntity<>(savedBookCondition, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all book conditions",
            description = "Returns a list of all book conditions",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of book conditions",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookConditionDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<BookConditionDto>> getAllBookConditions()
    {
        List<BookConditionDto> allBookConditions = bookConditionService.getAllBookConditions();

        return new ResponseEntity<>(allBookConditions, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a book condition by ID",
            description = "Returns a single book condition by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book condition found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookConditionDto.class))),
                    @ApiResponse(responseCode = "404", description = "Book condition not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<BookConditionDto> getBookConditionById(@PathVariable("id") Long bookConditionId)
    {
        BookConditionDto foundBookCondition = bookConditionService.getBookConditionById(bookConditionId);

        return new ResponseEntity<>(foundBookCondition, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a book condition",
            description = "Updates an existing book condition by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book condition updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookConditionDto.class))),
                    @ApiResponse(responseCode = "404", description = "Book condition not found", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<BookConditionDto> updateBookCondition(@PathVariable("id") Long bookConditionId, @RequestBody @Valid BookConditionDto bookConditionDto)
    {
        BookConditionDto updatedBookCondition = bookConditionService.updateBookCondition(bookConditionId, bookConditionDto);

        return new ResponseEntity<>(updatedBookCondition, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a book condition",
            description = "Deletes a book condition by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book condition deleted"),
                    @ApiResponse(responseCode = "404", description = "Book condition not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBookCondition(@PathVariable("id") Long bookConditionId)
    {
        bookConditionService.deleteBookCondition(bookConditionId);

        return new ResponseEntity<>("Book Condition with ID : " + bookConditionId + " deleted successfully !", HttpStatus.OK);
    }
}
