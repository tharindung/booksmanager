package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.BookCategoryDto;
import com.mybookscollection.BooksManager.service.BookCategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/book-categories")
@Tag(name = "Book Categories", description = "CRUD operations for book categories") //Groups all endpoints in this controller under a named tag
public class BookCategoryController {

    private BookCategoryService bookCategoryService;

    @Operation(
            summary = "Create a new book category",
            description = "Creates a new book category and returns it",
            responses = { //Specifies possible HTTP responses
                    @ApiResponse(responseCode = "201", description = "Book category created",
                            content = @Content(mediaType = "application/json", //Defines Response format
                                    schema = @Schema(implementation = BookCategoryDto.class))), //'implementation' defines the Java class used to generate the Schema for response
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content) //Specifies 'no body' expected'
            }
    )
    @PostMapping
    public ResponseEntity<BookCategoryDto> createBookCategory(@RequestBody @Valid BookCategoryDto bookCategoryDto)
    {
        BookCategoryDto savedBookCategory = bookCategoryService.createBookCategory(bookCategoryDto);

        return new ResponseEntity<>(savedBookCategory, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all book categories",
            description = "Returns a list of all book categories",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of book categories",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookCategoryDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<BookCategoryDto>> getAllBookCategories()
    {
        List<BookCategoryDto> allBookCategories = bookCategoryService.getAllBookCategories();

        return new ResponseEntity<>(allBookCategories, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a book category by ID",
            description = "Returns a single book category by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book category found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookCategoryDto.class))),
                    @ApiResponse(responseCode = "404", description = "Book category not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<BookCategoryDto> getBookCategoryById(@PathVariable("id") Long bookCategoryId)
    {
        BookCategoryDto foundBookCategory = bookCategoryService.getBookCategoryById(bookCategoryId);

        return new ResponseEntity<>(foundBookCategory, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a book category",
            description = "Updates an existing book category by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book category updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookCategoryDto.class))),
                    @ApiResponse(responseCode = "404", description = "Book category not found", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<BookCategoryDto> updateBookCategory(@PathVariable("id") Long bookCategoryId, @RequestBody @Valid BookCategoryDto bookCategoryDto){

        BookCategoryDto updatedBookCategory = bookCategoryService.updateBookCategory(bookCategoryId, bookCategoryDto);

        return new ResponseEntity<>(updatedBookCategory, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a book category",
            description = "Deletes a book category by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book category deleted"),
                    @ApiResponse(responseCode = "404", description = "Book category not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBookCategory(@PathVariable("id") Long bookCategoryId)
    {
        bookCategoryService.deleteBookCategory(bookCategoryId);

        return new ResponseEntity<>("Book Category with ID : " + bookCategoryId + " deleted successfully !", HttpStatus.OK);
    }
}
