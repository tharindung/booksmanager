package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.BookRequestDto;
import com.mybookscollection.BooksManager.service.BookRequestService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/book-requests")
public class BookRequestController {

    private BookRequestService bookRequestService;

    @PostMapping
    public ResponseEntity<BookRequestDto> createBookRequest(@RequestBody BookRequestDto bookRequestDto)
    {
        BookRequestDto savedBookRequest = bookRequestService.createBookRequest(bookRequestDto);

        return new ResponseEntity<>(savedBookRequest, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookRequestDto>> getAllBookRequests()
    {
        List<BookRequestDto> allBookRequests = bookRequestService.getAllBookRequests();

        return new ResponseEntity<>(allBookRequests, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookRequestDto> getBookRequestById(@PathVariable("id") Long bookRequestId)
    {
        BookRequestDto foundBookRequest = bookRequestService.getBookRequestById(bookRequestId);

        return new ResponseEntity<>(foundBookRequest, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookRequestDto> updateBookRequest(@PathVariable("id") Long bookRequestId, @RequestBody BookRequestDto bookRequestDto)
    {
        BookRequestDto updatedBookRequest = bookRequestService.updateBookRequest(bookRequestId, bookRequestDto);

        return new ResponseEntity<>(updatedBookRequest, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBookRequest(@PathVariable("id") Long bookRequestId)
    {
        bookRequestService.deleteBookRequest(bookRequestId);

        return new ResponseEntity<>("Book Request with ID : " + bookRequestId + " deleted successfully !", HttpStatus.OK);
    }
}
