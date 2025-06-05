package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookDto;
import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.exception.ResourceNotFoundException;
import com.mybookscollection.BooksManager.repository.BookRepository;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    private ModelMapper modelMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {

        Book newBook = modelMapper.map(bookDto, Book.class);

        Book savedBook = bookRepository.save(newBook);

        return modelMapper.map(savedBook, BookDto.class);
    }

    @Override
    public List<BookDto> getAllBooks() {

        List<Book> allBooks = bookRepository.findAll();

        return allBooks.stream().map((b)->modelMapper.map(b, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long bookId) {

        Book foundBook = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book with ID : " + bookId + " does not exist !"));

        return modelMapper.map(foundBook, BookDto.class);
    }

    @Override
    public BookDto updateBook(Long bookId, BookDto bookDto) {

        Book foundBook = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book with ID : " + bookId + " does not exist !"));

        foundBook.setBookName(bookDto.getBookName());
        foundBook.setBookAuthor(bookDto.getBookAuthor());
        foundBook.setBookCatalogNo(bookDto.getBookCatalogNo());
        foundBook.setBookCategory(bookDto.getBookCategory());
        foundBook.setBookCondition(bookDto.getBookCondition());
        foundBook.setBookImg(bookDto.getBookImg());
        foundBook.setBookNotes(bookDto.getBookNotes());
        foundBook.setBookOwner(bookDto.getBookOwner());
        foundBook.setBookPurchaseDate(bookDto.getBookPurchaseDate());
        foundBook.setBookRequests(bookDto.getBookRequests());
        foundBook.setBookShareable(bookDto.isBookShareable());

        Book updatedBook = bookRepository.save(foundBook);

        return modelMapper.map(updatedBook, BookDto.class);
    }

    @Override
    public void deleteBook(Long bookId) {

        Book foundBook = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book with ID : " + bookId + " does not exist !"));

        bookRepository.deleteById(bookId);

    }
}
