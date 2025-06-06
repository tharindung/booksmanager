package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.BookConditionDto;
import com.mybookscollection.BooksManager.service.BookConditionService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/book-conditions")
public class BookConditionController {

    private BookConditionService bookConditionService;

    @PostMapping
    public ResponseEntity<BookConditionDto> createBookCondition(@RequestBody BookConditionDto bookConditionDto)
    {
        BookConditionDto savedBookCondition = bookConditionService.createBookCondition(bookConditionDto);

        return new ResponseEntity<>(savedBookCondition, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookConditionDto>> getAllBookConditions()
    {
        List<BookConditionDto> allBookConditions = bookConditionService.getAllBookConditions();

        return new ResponseEntity<>(allBookConditions, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookConditionDto> getBookConditionById(@PathVariable("id") Long bookConditionId)
    {
        BookConditionDto foundBookCondition = bookConditionService.getBookConditionById(bookConditionId);

        return new ResponseEntity<>(foundBookCondition, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookConditionDto> updateBookCondition(@PathVariable("id") Long bookConditionId, @RequestBody BookConditionDto bookConditionDto)
    {
        BookConditionDto updatedBookCondition = bookConditionService.updateBookCondition(bookConditionId, bookConditionDto);

        return new ResponseEntity<>(updatedBookCondition, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBookCondition(@PathVariable("id") Long bookConditionId)
    {
        bookConditionService.deleteBookCondition(bookConditionId);

        return new ResponseEntity<>("Book Condition with ID : " + bookConditionId + " deleted successfully !", HttpStatus.OK);
    }
}
