package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.*;
import com.mybookscollection.BooksManager.entity.*;
import com.mybookscollection.BooksManager.repository.BookRequestRepository;
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
public class BookRequestServiceTests {

    @Mock
    private BookRequestRepository bookRequestRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private BookRequestServiceImpl bookRequestService;

    private BookRequest bookRequest;

    private BookRequestDto bookRequestDto;

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

    private User bookRequester;

    private UserDto bookRequesterDto;

    private UserDisplayDto bookRequesterDisplayDto;

    private RequestStatus requestStatus;

    private RequestStatusDto requestStatusDto;

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

        country = Country.builder().
                    countryId(1L).
                    country("Canada").build();

        countryDto = CountryDto.builder().
                        countryId(1L).
                        country("Canada").build();

        bookOwner = User.builder().
                    userId(1L).
                    userName("John Doe").
                    userEmail("john.doe@gmail.com").
                    userPassword("1234").
                    userCountry(country).build();

        bookOwnerDto = UserDto.builder().
                userId(1L).
                userName("John Doe").
                userEmail("john.doe@gmail.com").
                userPassword("1234").
                userCountry(countryDto).build();

        bookOwnerDisplayDto = UserDisplayDto.builder().
                                userId(1L).
                                userName("John Doe").
                                userEmail("john.doe@gmail.com").
                                userCountry(countryDto).build();

        book = Book.builder().
                bookId(1L).
                bookName("Harry Potter").
                bookAuthor("J.K.Rowling").
                bookCategory(bookCategory).
                bookCondition(bookCondition).
                bookCatalogNo("HP1234").
                isBookShareable(true).
                bookOwner(bookOwner).build();

        bookDto = BookDto.builder().
                    bookId(1L).
                    bookName("Harry Potter").
                    bookAuthor("J.K.Rowling").
                    bookCategory(bookCategoryDto).
                    bookCondition(bookConditionDto).
                    bookCatalogNo("HP1234").
                    bookShareable(true).
                    bookOwner(bookOwnerDisplayDto).build();

        bookRequester = User.builder().
                userId(2L).
                userName("Jane Doe").
                userEmail("jane.doe@gmail.com").
                userPassword("56789").
                userCountry(country).build();

        bookRequesterDto = UserDto.builder().
                userId(2L).
                userName("Jane Doe").
                userEmail("jane.doe@gmail.com").
                userPassword("56789").
                userCountry(countryDto).build();

        bookRequesterDisplayDto = UserDisplayDto.builder().
                userId(2L).
                userName("Jane Doe").
                userEmail("jane.doe@gmail.com").
                userCountry(countryDto).build();

        requestStatus = RequestStatus.builder().
                        requestStatusId(1L).
                        requestStatus("Pending").build();

        requestStatusDto = RequestStatusDto.builder().
                            requestStatusId(1L).
                            requestStatus("Pending").build();

        bookRequest = BookRequest.builder().
                        bookRequestId(1L).
                        book(book).
                        requestedUser(bookRequester).
                        requestStatus(requestStatus).build();

        bookRequestDto = BookRequestDto.builder().
                            bookRequestId(1L).
                            book(bookDto).
                            requestedUser(bookRequesterDisplayDto).
                            requestStatus(requestStatusDto).build();
    }

    //Junit test for createBookRequest method
    @DisplayName("Junit test for createBookRequest method")
    @Test
    public void givenBookRequestDtoObject_whenCreateBookRequest_thenReturnSavedBookRequestDtoObject(){

        //given - precondition or setup
        given(bookRequestRepository.save(bookRequest)).willReturn(bookRequest);

        doReturn(bookRequestDto).when(modelMapper).map(bookRequest, BookRequestDto.class);
        doReturn(bookRequest).when(modelMapper).map(bookRequestDto, BookRequest.class);

        //when - action or the behavior we're testing
        BookRequestDto savedBookRequest = bookRequestService.createBookRequest(bookRequestDto);

        //then - verify the output
        Assertions.assertThat(savedBookRequest).isNotNull();
    }

    //Junit test for getAllBookRequests positive scenario
    @DisplayName("Junit test for getAllBookRequests positive scenario")
    @Test
    public void givenBookRequestList_whenGetAllBookRequests_thenReturnBookRequestDtoList(){

        //given - precondition or setup
        BookRequest bookRequest1 = BookRequest.builder().
                                    bookRequestId(2L).
                                    book(book).
                                    requestedUser(bookRequester).requestStatus(requestStatus).build();

        given(bookRequestRepository.findAll()).willReturn(List.of(bookRequest, bookRequest1));

        //when - action or the behavior we're testing
        List<BookRequestDto> allBookRequests = bookRequestService.getAllBookRequests();

        //then - verify the output
        Assertions.assertThat(allBookRequests).isNotNull();
        Assertions.assertThat(allBookRequests.size()).isEqualTo(2);
    }

    //Junit test for getAllBookRequests negative scenario
    @DisplayName("Junit test for getAllBookRequests negative scenario")
    @Test
    public void givenEmptyBookRequestList_whenGetAllBookRequests_thenReturnEmptyBookRequestDtoList(){

        //given - precondition or setup
        given(bookRequestRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        List<BookRequestDto> allBookRequests = bookRequestService.getAllBookRequests();

        //then - verify the output
        Assertions.assertThat(allBookRequests).isEmpty();
    }

    //Junit test for getBookRequestById method
    @DisplayName("Junit test for getBookRequestById method")
    @Test
    public void givenBookRequestId_whenGetBookRequestById_thenReturnBookRequestDtoObjet(){

        //given - precondition or setup
        given(bookRequestRepository.findById(bookRequest.getBookRequestId())).willReturn(Optional.of(bookRequest));

        doReturn(bookRequestDto).when(modelMapper).map(bookRequest, BookRequestDto.class);

        //when - action or the behavior we're testing
        BookRequestDto returnedBookRequest = bookRequestService.getBookRequestById(bookRequest.getBookRequestId());

        //then - verify the output
        Assertions.assertThat(returnedBookRequest).isNotNull();
    }

    //Junit test for updateBookRequest
    @DisplayName("Junit test for updateBookRequest")
    @Test
    public void givenBookRequestDto_whenUpdateBookRequest_thenReturnUpdatedBookRequestDto(){

        //given - precondition or setup
        given(bookRequestRepository.findById(bookRequest.getBookRequestId())).willReturn(Optional.of(bookRequest));

        given(userService.getUserDtoById(bookRequestDto.getRequestedUser().getUserId())).willReturn(bookRequesterDto);

        given(userService.getUserDtoById(bookRequestDto.getBook().getBookOwner().getUserId())).willReturn(bookOwnerDto);

        given(bookRequestRepository.save(bookRequest)).willReturn(bookRequest);

        RequestStatus requestStatus1 = RequestStatus.builder().requestStatusId(2L).requestStatus("Approved").build();

        RequestStatusDto requestStatusDto1 = RequestStatusDto.builder().requestStatusId(2L).requestStatus("Approved").build();

        doReturn(bookRequestDto).when(modelMapper).map(bookRequest, BookRequestDto.class);
        doReturn(book).when(modelMapper).map(bookRequestDto.getBook(), Book.class);
        doReturn(bookOwner).when(modelMapper).map(bookOwnerDto, User.class);
        doReturn(bookRequester).when(modelMapper).map(bookRequesterDto, User.class);
        doReturn(requestStatus1).when(modelMapper).map(requestStatusDto1, RequestStatus.class);

        bookRequest.setRequestStatus(requestStatus1);

        bookRequestDto.setRequestStatus(requestStatusDto1);

        //when - action or the behavior we're testing
        BookRequestDto updatedBookRequest = bookRequestService.updateBookRequest(bookRequestDto.getBookRequestId(), bookRequestDto);

        //then - verify the output
        Assertions.assertThat(updatedBookRequest.getRequestStatus().getRequestStatus()).isEqualTo("Approved");
    }

    //Junit test for deleteBookRequest method
    @DisplayName("Junit test for deleteBookRequest method")
    @Test
    public void givenBookRequestId_whenDeleteBookRequest_thenReturnNothing(){

        //given - precondition or setup
        given(bookRequestRepository.findById(bookRequest.getBookRequestId())).willReturn(Optional.of(bookRequest));

        willDoNothing().given(bookRequestRepository).deleteById(bookRequest.getBookRequestId());

        //when - action or the behavior we're testing
        bookRequestService.deleteBookRequest(bookRequestDto.getBookRequestId());

        //then - verify the output
        verify(bookRequestRepository, times(1)).deleteById(bookRequestDto.getBookRequestId());
    }
}
