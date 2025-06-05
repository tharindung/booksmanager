package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookConditionDto;

import java.util.List;

public interface BookConditionService {

    BookConditionDto createBookCondition(BookConditionDto bookConditionDto);

    List<BookConditionDto> getAllBookConditions();

    BookConditionDto getBookConditionById(Long bookConditionId);

    BookConditionDto updateBookCondition(Long bookConditionId, BookConditionDto bookConditionDto);

    void deleteBookCondition(Long bookConditionId);
}
