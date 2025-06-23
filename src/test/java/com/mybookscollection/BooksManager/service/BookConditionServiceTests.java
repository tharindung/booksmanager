package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookCategoryDto;
import com.mybookscollection.BooksManager.dto.BookConditionDto;
import com.mybookscollection.BooksManager.entity.BookCondition;
import com.mybookscollection.BooksManager.repository.BookConditionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookConditionServiceTests {

    @Mock
    private BookConditionRepository bookConditionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookConditionServiceImpl bookConditionService;

    private BookCondition bookCondition;

    private BookConditionDto bookConditionDto;

    @BeforeEach
    public void setup()
    {
        bookCondition = BookCondition.builder().
                bookConditionId(1L).
                bookCondition("Good").build();

        bookConditionDto = BookConditionDto.builder().
                            bookConditionId(1L).
                            bookCondition("Good").build();
    }

    //Junit test for createBookCondition method
    @DisplayName("Junit test for createBookCondition method")
    @Test
    public void givenBookConditionDtoObject_whenCreateBookCondition_thenReturnSavedBookConditionDtoObject(){

        //given - precondition or setup
        given(bookConditionRepository.save(bookCondition)).willReturn(bookCondition);

        doReturn(bookConditionDto).when(modelMapper).map(bookCondition, BookConditionDto.class);
        doReturn(bookCondition).when(modelMapper).map(bookConditionDto, BookCondition.class);

        //when - action or the behavior we're testing
        BookConditionDto savedBookCondition = bookConditionService.createBookCondition(bookConditionDto);

        //then - verify the output
        Assertions.assertThat(savedBookCondition).isNotNull();
    }

    //Junit test for getAllBookConditions positive scenario
    @DisplayName("Junit test for getAllBookConditions positive scenario")
    @Test
    public void givenBookConditionList_whenGetAllBookConditions_thenReturnBookConditionDtoList(){

        //given - precondition or setup
        BookCondition bookCondition1 = BookCondition.builder().
                                        bookConditionId(2L).
                                        bookCondition("Used").build();

        given(bookConditionRepository.findAll()).willReturn(List.of(bookCondition, bookCondition1));

        //when - action or the behavior we're testing
        List<BookConditionDto> allBookConditions = bookConditionService.getAllBookConditions();

        //then - verify the output
        Assertions.assertThat(allBookConditions).isNotNull();
        Assertions.assertThat(allBookConditions.size()).isEqualTo(2);
    }

    //Junit test for getAllBookConditions negative scenario
    @DisplayName("Junit test for getAllBookConditions negative scenario")
    @Test
    public void givenEmptyBookConditionList_whenGetAllBookConditions_thenReturnEmptyBookConditionDtoList(){

        //given - precondition or setup
        given(bookConditionRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        List<BookConditionDto> allBookConditions = bookConditionService.getAllBookConditions();

        //then - verify the output
        Assertions.assertThat(allBookConditions).isEmpty();
        Assertions.assertThat(allBookConditions.size()).isEqualTo(0);
    }

    //Junit test for getBookConditionById method
    @DisplayName("Junit test for getBookConditionById method")
    @Test
    public void givenBookConditionId_whenGetBookConditionById_thenReturnBookConditionDtoObject(){

        //given - precondition or setup
        given(bookConditionRepository.findById(bookCondition.getBookConditionId())).willReturn(Optional.of(bookCondition));

        doReturn(bookConditionDto).when(modelMapper).map(bookCondition, BookConditionDto.class);

        //when - action or the behavior we're testing
        BookConditionDto returnedBookCondition = bookConditionService.getBookConditionById(bookCondition.getBookConditionId());

        //then - verify the output
        Assertions.assertThat(returnedBookCondition).isNotNull();
    }

    //Junit test for updateBookCondition method
    @DisplayName("Junit test for updateBookCondition method")
    @Test
    public void givenBookConditionDtoObject_whenUpdateBookCondition_thenReturnUpdatedBookConditionDtoObject(){

        //given - precondition or setup
        given(bookConditionRepository.findById(bookCondition.getBookConditionId())).willReturn(Optional.of(bookCondition));

        given(bookConditionRepository.save(bookCondition)).willReturn(bookCondition);

        doReturn(bookConditionDto).when(modelMapper).map(bookCondition, BookConditionDto.class);

        bookCondition.setBookCondition("Worn Out");

        bookConditionDto.setBookCondition("Worn Out");

        //when - action or the behavior we're testing
        BookConditionDto updatedBookCondition = bookConditionService.updateBookCondition(bookConditionDto.getBookConditionId(), bookConditionDto);

        //then - verify the output
        Assertions.assertThat(updatedBookCondition.getBookCondition()).isEqualTo("Worn Out");
    }

    //Junit test for deleteBookCondition method
    @DisplayName("Junit test for deleteBookCondition method")
    @Test
    public void givenBookConditionId_whenDeleteBookCondition_thenReturnNothing(){

        //given - precondition or setup
        given(bookConditionRepository.findById(bookCondition.getBookConditionId())).willReturn(Optional.of(bookCondition));

        willDoNothing().given(bookConditionRepository).deleteById(bookCondition.getBookConditionId());

        //when - action or the behavior we're testing
        bookConditionService.deleteBookCondition(bookConditionDto.getBookConditionId());

        //then - verify the output
        verify(bookConditionRepository, times(1)).deleteById(bookConditionDto.getBookConditionId());
    }
}
