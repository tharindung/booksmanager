package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookCategoryDto;

import java.util.List;

public interface BookCategoryService {

    BookCategoryDto createBookCategory(BookCategoryDto bookCategoryDto);

    List<BookCategoryDto> getAllBookCategories();

    BookCategoryDto getBookCategoryById(Long bookCategoryId);

    BookCategoryDto updateBookCategory(Long bookCategoryId, BookCategoryDto bookCategoryDto);

    void deleteBookCategory(Long bookCategoryId);
}
