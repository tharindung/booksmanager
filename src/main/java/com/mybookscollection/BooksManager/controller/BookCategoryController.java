package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.BookCategoryDto;
import com.mybookscollection.BooksManager.service.BookCategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/book-categories")
public class BookCategoryController {

    private BookCategoryService bookCategoryService;

    @PostMapping
    public ResponseEntity<BookCategoryDto> createBookCategory(@RequestBody @Valid BookCategoryDto bookCategoryDto)
    {
        BookCategoryDto savedBookCategory = bookCategoryService.createBookCategory(bookCategoryDto);

        return new ResponseEntity<>(savedBookCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookCategoryDto>> getAllBookCategories()
    {
        List<BookCategoryDto> allBookCategories = bookCategoryService.getAllBookCategories();

        return new ResponseEntity<>(allBookCategories, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookCategoryDto> getBookCategoryById(@PathVariable("id") Long bookCategoryId)
    {
        BookCategoryDto foundBookCategory = bookCategoryService.getBookCategoryById(bookCategoryId);

        return new ResponseEntity<>(foundBookCategory, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookCategoryDto> updateBookCategory(@PathVariable("id") Long bookCategoryId, @RequestBody @Valid BookCategoryDto bookCategoryDto){

        BookCategoryDto updatedBookCategory = bookCategoryService.updateBookCategory(bookCategoryId, bookCategoryDto);

        return new ResponseEntity<>(updatedBookCategory, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBookCategory(@PathVariable("id") Long bookCategoryId)
    {
        bookCategoryService.deleteBookCategory(bookCategoryId);

        return new ResponseEntity<>("Book Category with ID : " + bookCategoryId + " deleted successfully !", HttpStatus.OK);
    }
}
