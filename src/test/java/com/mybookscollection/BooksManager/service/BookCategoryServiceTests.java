package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.BookCategoryDto;
import com.mybookscollection.BooksManager.entity.BookCategory;
import com.mybookscollection.BooksManager.repository.BookCategoryRepository;
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
public class BookCategoryServiceTests {

    @Mock
    private BookCategoryRepository bookCategoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookCategoryServiceImpl bookCategoryService;

    private BookCategory bookCategory;

    private BookCategoryDto bookCategoryDto;

    @BeforeEach
    public void setup()
    {
        bookCategory = BookCategory.builder().
                        bookCategoryId(1L).
                        bookCategory("Sports").build();

        bookCategoryDto = BookCategoryDto.builder().
                            bookCategoryId(1L).
                            bookCategory("Sports").build();
    }

    //Junit test for createBookCategory method
    @DisplayName("Junit test for createBookCategory method")
    @Test
    public void givenBookCategoryDtoObject_whenCreateBookCategory_thenReturnSavedBookCategoryDtoObject(){

        //given - precondition or setup
        given(bookCategoryRepository.save(bookCategory)).willReturn(bookCategory);

        doReturn(bookCategory).when(modelMapper).map(bookCategoryDto, BookCategory.class);
        doReturn(bookCategoryDto).when(modelMapper).map(bookCategory, BookCategoryDto.class);

        //when - action or the behavior we're testing
        BookCategoryDto savedBookCategory = bookCategoryService.createBookCategory(bookCategoryDto);

        //then - verify the output
        Assertions.assertThat(savedBookCategory).isNotNull();
    }

    //Junit test for getAllBookCategories method positive scenario
    @DisplayName("Junit test for getAllBookCategories method positive scenario")
    @Test
    public void givenBookCategoryList_whenGetAllBookCategories_thenReturnBookCategoryDtoList(){

        //given - precondition or setup
        BookCategory bookCategory1 = BookCategory.builder().bookCategoryId(2L).bookCategory("Children").build();

        given(bookCategoryRepository.findAll()).willReturn(List.of(bookCategory, bookCategory1));

        //when - action or the behavior we're testing
        List<BookCategoryDto> allBooks = bookCategoryService.getAllBookCategories();

        //then - verify the output
        Assertions.assertThat(allBooks).isNotNull();
        Assertions.assertThat(allBooks.size()).isEqualTo(2);
    }

    //Junit test for getAllBookCategories method negative scenario
    @DisplayName("Junit test for getAllBookCategories method negative scenario")
    @Test
    public void givenEmptyBookCategoryList_whenGetAllBookCategories_thenReturnEmptyBookCategoryDtoList(){

        //given - precondition or setup
        given(bookCategoryRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        List<BookCategoryDto> allBookCategories = bookCategoryService.getAllBookCategories();

        //then - verify the output
        Assertions.assertThat(allBookCategories).isEmpty();
        Assertions.assertThat(allBookCategories.size()).isEqualTo(0);
    }

    //Junit test for getBookCategoryById method
    @DisplayName("Junit test for getBookCategoryById method")
    @Test
    public void givenBookCategoryId_whenGetBookCategoryById_thenReturnBookCategoryDtoObject(){

        //given - precondition or setup
        given(bookCategoryRepository.findById(bookCategory.getBookCategoryId())).willReturn(Optional.of(bookCategory));

        doReturn(bookCategoryDto).when(modelMapper).map(bookCategory, BookCategoryDto.class);

        //when - action or the behavior we're testing
        BookCategoryDto returnedBookCategory = bookCategoryService.getBookCategoryById(bookCategory.getBookCategoryId());

        //then - verify the output
        Assertions.assertThat(returnedBookCategory).isNotNull();
    }

    //Junit test for updateBookCategory method
    @DisplayName("Junit test for updateBookCategory method")
    @Test
    public void givenBookCategoryDtoObject_whenUpdateBookCategory_thenReturnUpdatedBookCategoryDtoObject(){

        //given - precondition or setup
        given(bookCategoryRepository.findById(bookCategory.getBookCategoryId())).willReturn(Optional.of(bookCategory));

        given(bookCategoryRepository.save(bookCategory)).willReturn(bookCategory);

        doReturn(bookCategoryDto).when(modelMapper).map(bookCategory, BookCategoryDto.class);

        bookCategory.setBookCategory("Cooking");

        bookCategoryDto.setBookCategory("Cooking");

        //when - action or the behavior we're testing
        BookCategoryDto updatedBookCategory = bookCategoryService.updateBookCategory(bookCategoryDto.getBookCategoryId(), bookCategoryDto);

        //then - verify the output
        Assertions.assertThat(updatedBookCategory.getBookCategory()).isEqualTo("Cooking");
    }

    //Junit test for deleteBookCategory method
    @DisplayName("Junit test for deleteBookCategory method")
    @Test
    public void givenBookCategoryId_whenDeleteBookCategory_thenReturnNothing(){

        //given - precondition or setup
        given(bookCategoryRepository.findById(bookCategory.getBookCategoryId())).willReturn(Optional.of(bookCategory));

        willDoNothing().given(bookCategoryRepository).deleteById(bookCategory.getBookCategoryId());

        //when - action or the behavior we're testing
        bookCategoryService.deleteBookCategory(bookCategoryDto.getBookCategoryId());

        //then - verify the output
        verify(bookCategoryRepository, times(1)).deleteById(bookCategoryDto.getBookCategoryId());
    }
}
