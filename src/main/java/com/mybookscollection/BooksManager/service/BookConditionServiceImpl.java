package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookConditionDto;
import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.entity.BookCondition;
import com.mybookscollection.BooksManager.exception.ResourceNotFoundException;
import com.mybookscollection.BooksManager.repository.BookConditionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookConditionServiceImpl implements BookConditionService{

    private BookConditionRepository bookConditionRepository;

    private ModelMapper modelMapper;

    @Override
    public BookConditionDto createBookCondition(BookConditionDto bookConditionDto) {

        BookCondition newBookCondition = modelMapper.map(bookConditionDto, BookCondition.class);

        BookCondition savedBookCondition = bookConditionRepository.save(newBookCondition);

        return modelMapper.map(savedBookCondition, BookConditionDto.class);
    }

    @Override
    public List<BookConditionDto> getAllBookConditions() {

        List<BookCondition> allBookConditions = bookConditionRepository.findAll();

        return allBookConditions.stream().map((bc)->modelMapper.map(bc, BookConditionDto.class)).collect(Collectors.toList());
    }

    @Override
    public BookConditionDto getBookConditionById(Long bookConditionId) {

        //BookCondition foundBookCondition = bookConditionRepository.findById(bookConditionId).orElseThrow(()->new ResourceNotFoundException("Book Condition with ID : "+bookConditionId+" does not exist !"));
        BookCondition foundBookCondition = bookConditionRepository.findById(bookConditionId).orElseThrow(()->new ResourceNotFoundException("Book Condition", "bookConditionId", bookConditionId));

        return modelMapper.map(foundBookCondition, BookConditionDto.class);
    }

    @Override
    public BookConditionDto updateBookCondition(Long bookConditionId, BookConditionDto bookConditionDto) {

        //BookCondition foundBookCondition = bookConditionRepository.findById(bookConditionId).orElseThrow(()->new ResourceNotFoundException("Book Condition with ID : "+bookConditionId+" does not exist !"));
        BookCondition foundBookCondition = bookConditionRepository.findById(bookConditionId).orElseThrow(()->new ResourceNotFoundException("Book Condition", "bookConditionId", bookConditionId));

        foundBookCondition.setBookCondition(bookConditionDto.getBookCondition());
        //foundBookCondition.setBooks(bookConditionDto.getBooks());
        foundBookCondition.setBooks(bookConditionDto.getBooks().stream().map((b)->modelMapper.map(b, Book.class)).collect(Collectors.toSet()));

        BookCondition updatedBookCondition = bookConditionRepository.save(foundBookCondition);

        return modelMapper.map(updatedBookCondition, BookConditionDto.class);
    }

    @Override
    public void deleteBookCondition(Long bookConditionId) {

        //BookCondition foundBookCondition = bookConditionRepository.findById(bookConditionId).orElseThrow(()->new ResourceNotFoundException("Book Condition with ID : "+bookConditionId+" does not exist !"));
        BookCondition foundBookCondition = bookConditionRepository.findById(bookConditionId).orElseThrow(()->new ResourceNotFoundException("Book Condition", "bookConditionId", bookConditionId));

        bookConditionRepository.deleteById(bookConditionId);

    }
}
