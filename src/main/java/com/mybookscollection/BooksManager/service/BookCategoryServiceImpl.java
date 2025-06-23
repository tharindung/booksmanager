package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookCategoryDto;
import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.entity.BookCategory;
import com.mybookscollection.BooksManager.exception.ResourceNotFoundException;
import com.mybookscollection.BooksManager.repository.BookCategoryRepository;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookCategoryServiceImpl implements BookCategoryService{

    private BookCategoryRepository bookCategoryRepository;

    private ModelMapper modelMapper;

    @Override
    public BookCategoryDto createBookCategory(BookCategoryDto bookCategoryDto) {

        BookCategory newBookCategory = modelMapper.map(bookCategoryDto, BookCategory.class);

        BookCategory savedBookCategory = bookCategoryRepository.save(newBookCategory);

        return modelMapper.map(savedBookCategory, BookCategoryDto.class);
    }

    @Override
    public List<BookCategoryDto> getAllBookCategories() {

        List<BookCategory> allBookCategories = bookCategoryRepository.findAll();

        return allBookCategories.stream().map((b)->modelMapper.map(b, BookCategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public BookCategoryDto getBookCategoryById(Long bookCategoryId) {

        //BookCategory foundBookCategory = bookCategoryRepository.findById(bookCategoryId).orElseThrow(()->new ResourceNotFoundException("Book Category with ID : "+bookCategoryId+" does not exist !"));
        BookCategory foundBookCategory = bookCategoryRepository.findById(bookCategoryId).orElseThrow(()->new ResourceNotFoundException("Book Category", "bookCategoryId", bookCategoryId));
        return modelMapper.map(foundBookCategory, BookCategoryDto.class);
    }

    @Override
    public BookCategoryDto updateBookCategory(Long bookCategoryId, BookCategoryDto bookCategoryDto) {

        //BookCategory foundBookCategory = bookCategoryRepository.findById(bookCategoryId).orElseThrow(()->new ResourceNotFoundException("Book Category with ID : "+bookCategoryId+" does not exist !"));
        BookCategory foundBookCategory = bookCategoryRepository.findById(bookCategoryId).orElseThrow(()->new ResourceNotFoundException("Book Category", "bookCategoryId", bookCategoryId));

        foundBookCategory.setBookCategory(bookCategoryDto.getBookCategory());
        //foundBookCategory.setBooks(bookCategoryDto.getBooks());
        /* Before access 'stream()' method we need to make sure books set is not null  */
        if(bookCategoryDto.getBooks() != null)
        {
            foundBookCategory.setBooks(bookCategoryDto.getBooks().stream().map((b)->modelMapper.map(b, Book.class)).collect(Collectors.toSet()));
        }
        else
        {
            foundBookCategory.setBooks(null);
        }

        BookCategory updatedBookCategory = bookCategoryRepository.save(foundBookCategory);

        return modelMapper.map(updatedBookCategory, BookCategoryDto.class);
    }

    @Override
    public void deleteBookCategory(Long bookCategoryId) {

        //BookCategory foundBookCategory = bookCategoryRepository.findById(bookCategoryId).orElseThrow(()->new ResourceNotFoundException("Book Category with ID : "+bookCategoryId+" does not exist !"));
        BookCategory foundBookCategory = bookCategoryRepository.findById(bookCategoryId).orElseThrow(()->new ResourceNotFoundException("Book Category", "bookCategoryId", bookCategoryId));

        bookCategoryRepository.deleteById(bookCategoryId);
    }
}
