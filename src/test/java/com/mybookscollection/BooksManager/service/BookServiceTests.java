package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.*;
import com.mybookscollection.BooksManager.entity.*;
import com.mybookscollection.BooksManager.repository.BookRepository;
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
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserServiceImpl userService; //Being used to get UserDtoObject from UserDisplyDto object

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    private BookDto bookDto;

    private BookCategory bookCategory;

    private BookCategoryDto bookCategoryDto;

    private BookCondition bookCondition;

    private BookConditionDto bookConditionDto;

    private User bookOwner;

    private UserDto bookOwnerDto;

    private UserDisplayDto bookOwnerDisplayDto;

    private Country country;

    private CountryDto countryDto;

    @BeforeEach
    public void setup()
    {
        bookCategory = BookCategory.builder().
                        bookCategoryId(1L).
                        bookCategory("Children").build();

        bookCategoryDto = BookCategoryDto.builder().
                            bookCategoryId(1L).
                            bookCategory("Children").build();

        bookCondition = BookCondition.builder().
                            bookConditionId(1L).
                            bookCondition("New").build();

        bookConditionDto = BookConditionDto.builder().
                            bookConditionId(1L).
                            bookCondition("New").build();

        country = Country.builder().countryId(1L).country("Canada").build();

        countryDto = CountryDto.builder().countryId(1L).country("Canada").build();

        bookOwner = User.builder().userId(1L).userName("John Doe").userEmail("john.doe@gmail.com").userPassword("12345").userCountry(country).build();

        bookOwnerDto = UserDto.builder().userId(1L).userName("John Doe").userEmail("john.doe@gmail.com").userPassword("12345").userCountry(countryDto).build();

        bookOwnerDisplayDto = UserDisplayDto.builder().userId(1L).userName("John Doe").userEmail("john.doe@gmail.com").userCountry(countryDto).build();


        book = Book.builder().
                bookId(1L).
                bookName("Harry Potter").
                bookAuthor("J.K.Rowling").
                bookCatalogNo("HP1234").
                bookCategory(bookCategory).
                bookCondition(bookCondition).
                isBookShareable(true).
                bookOwner(bookOwner).build();

        bookDto = BookDto.builder().
                    bookId(1L).
                    bookName("Harry Potter").
                    bookAuthor("J.K.Rowling").
                    bookCatalogNo("HP1234").
                    bookCategory(bookCategoryDto).
                    bookCondition(bookConditionDto).
                    bookShareable(true).
                    bookOwner(bookOwnerDisplayDto).build();
    }

    //Junit test for createBook method
    @DisplayName("Junit test for createBook method")
    @Test
    public void givenBookDtoObject_whenCreateBook_thenRetunSavedBookDtoObject(){

        //given - precondition or setup
        given(bookRepository.save(book)).willReturn(book);

        doReturn(bookDto).when(modelMapper).map(book, BookDto.class);
        doReturn(book).when(modelMapper).map(bookDto, Book.class);

        //when - action or the behavior we're testing
        BookDto savedBook = bookService.createBook(bookDto);

        //then - verify the output
        Assertions.assertThat(savedBook).isNotNull();
    }

    //Junit test for getAllBooks method positive scenario
    @DisplayName("Junit test for getAllBooks method positive scenario")
    @Test
    public void givenBookList_whenGetAllBooks_thenReturnBookDtoList(){

        //given - precondition or setup
        Book book1 = Book.builder().
                        bookId(2L).
                        bookName("Famous Five").
                        bookAuthor("Enid Blyton").
                        bookCatalogNo("FF1234").
                        bookCategory(bookCategory).
                        bookCondition(bookCondition).
                        isBookShareable(false).
                        bookOwner(bookOwner).build();

        given(bookRepository.findAll()).willReturn(List.of(book, book1));

        //when - action or the behavior we're testing
        List<BookDto> allBooks = bookService.getAllBooks();

        //then - verify the output
        Assertions.assertThat(allBooks).isNotNull();
        Assertions.assertThat(allBooks.size()).isEqualTo(2);
    }

    //Junit test for getAllBooks method negative scenario
    @DisplayName("Junit test for getAllBooks method negative scenario")
    @Test
    public void givenEmptyBookList_whenGetAllBooks_thenReturnEmptyBookDtoList(){

        //given - precondition or setup
        given(bookRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        List<BookDto> allBooks = bookService.getAllBooks();

        //then - verify the output
        Assertions.assertThat(allBooks).isEmpty();
    }

    //Junit test for getBookById method
    @DisplayName("Junit test for getBookById method")
    @Test
    public void givenBookId_whenGetBookById_thenReturnBookDtoObject(){

        //given - precondition or setup
        given(bookRepository.findById(book.getBookId())).willReturn(Optional.of(book));

        doReturn(bookDto).when(modelMapper).map(book, BookDto.class);

        //when - action or the behavior we're testing
        BookDto returnedBook = bookService.getBookById(book.getBookId());

        //then - verify the output
        Assertions.assertThat(returnedBook).isNotNull();
    }

    //Junit test for updateBook method
    @DisplayName("Junit test for updateBook method")
    @Test
    public void givenBookDtoObject_whenUpdateBook_thenReturnUpdatedBookDtoObject(){

        //given - precondition or setup
        given(bookRepository.findById(book.getBookId())).willReturn(Optional.of(book));

        given(bookRepository.save(book)).willReturn(book);

        doReturn(bookDto).when(modelMapper).map(book, BookDto.class);
        doReturn(bookCategory).when(modelMapper).map(bookCategoryDto, BookCategory.class);
        doReturn(bookCondition).when(modelMapper).map(bookConditionDto, BookCondition.class);

        given(userService.getUserDtoById(bookDto.getBookOwner().getUserId())).willReturn(bookOwnerDto); //This was added to stub userService
        //doReturn(bookOwnerDto).when(modelMapper).map(bookOwnerDisplayDto, UserDto.class);
        doReturn(bookOwner).when(modelMapper).map(bookOwnerDto, User.class);

        book.setBookName("The Adventures of Tin Tin");
        book.setBookAuthor("Herge");

        bookDto.setBookName("The Adventures of Tin Tin");
        bookDto.setBookAuthor("Herge");

        //when - action or the behavior we're testing
        BookDto updatedBook = bookService.updateBook(bookDto.getBookId(), bookDto);

        //then - verify the output
        Assertions.assertThat(updatedBook.getBookName()).isEqualTo("The Adventures of Tin Tin");
        Assertions.assertThat(updatedBook.getBookAuthor()).isEqualTo("Herge");
    }

    //Junit test for deleteBook method
    @DisplayName("Junit test for deleteBook method")
    @Test
    public void givenBookId_whenDeleteBook_thenReturnNothing(){

        //given - precondition or setup
        given(bookRepository.findById(book.getBookId())).willReturn(Optional.of(book));

        willDoNothing().given(bookRepository).deleteById(book.getBookId());

        //when - action or the behavior we're testing
        bookService.deleteBook(bookDto.getBookId());

        //then - verify the output
        verify(bookRepository, times(1)).deleteById(bookDto.getBookId());
    }
}
