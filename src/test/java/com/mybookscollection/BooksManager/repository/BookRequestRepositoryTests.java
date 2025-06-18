package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookRequestRepositoryTests {

    @Autowired
    private BookRequestRepository bookRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RequestStatusRepository requestStatusRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private BookConditionRepository bookConditionRepository;

    private Book book;

    private Book savedBook;

    private User requestedUser;

    private User savedRequestedUser;

    private RequestStatus requestStatus;

    private RequestStatus savedRequestStatus;

    private BookRequest bookRequest;

    private Country country;

    private Country savedCountry;

    private BookCategory bookCategory;

    private BookCategory savedBookCategory;

    private BookCondition bookCondition;

    private BookCondition savedBookCondition;

    private User bookOwner;

    private User savedBookOwner;

    @BeforeEach
    public void setup()
    {
        country = Country.builder().country("France").build();

        savedCountry = countryRepository.save(country);

        requestedUser = User.builder().
                        userName("Jane Doe").
                        userEmail("jane.doe@gmail.com").
                        userPassword("12345").
                        userCountry(savedCountry).
                        userJoinedDate(LocalDate.parse("2025-05-15")).build();

        savedRequestedUser = userRepository.save(requestedUser);

        requestStatus = RequestStatus.builder().requestStatus("Approved").build();

        savedRequestStatus = requestStatusRepository.save(requestStatus);

        bookCategory = BookCategory.builder().bookCategory("History").build();

        savedBookCategory = bookCategoryRepository.save(bookCategory);

        bookCondition = BookCondition.builder().bookCondition("Worn Out").build();

        savedBookCondition = bookConditionRepository.save(bookCondition);

        bookOwner = User.builder().
                    userName("Mark Smith").
                    userEmail("mark.smith@gmail.com").
                    userPassword("67890").
                    userCountry(savedCountry).
                    userJoinedDate(LocalDate.parse("2025-04-05")).build();

        savedBookOwner = userRepository.save(bookOwner);

        book = Book.builder().
                bookName("Game of Thrones").
                bookAuthor("George R.R. Martin").
                bookCatalogNo("GT1234").
                bookCategory(savedBookCategory).
                bookCondition(savedBookCondition).
                isBookShareable(true).
                bookOwner(savedBookOwner).build();

        savedBook = bookRepository.save(book);

        bookRequest = BookRequest.builder().
                        book(savedBook).
                        requestedUser(savedRequestedUser).
                        requestStatus(savedRequestStatus).build();
    }

    //Junit test for create Book Request Operation
    @DisplayName("Junit test for create Book Request Operation")
    @Test
    public void givenBookRequestObject_whenSave_thenReturnSavedBookRequestObject()
    {
        //Given - precondition or setup

        //When - action or the behavior we're testing
        BookRequest savedBookRequest = bookRequestRepository.save(bookRequest);

        //Then - verify the output
        Assertions.assertThat(savedBookRequest).isNotNull();
        Assertions.assertThat(savedBookRequest.getBookRequestId()).isGreaterThan(0);
    }

    //Junit test for get all book requests operation
    @DisplayName("Junit test for get all book requests operation")
    @Test
    public void givenBookRequestList_whenFindAll_thenReturnBookRequestList()
    {
        //Given - precondition or setup
        bookRequestRepository.save(bookRequest);

        BookRequest bookRequest1 = BookRequest.builder().
                                    book(savedBook).
                                    requestedUser(savedRequestedUser).
                                    requestStatus(savedRequestStatus).build();

        bookRequestRepository.save(bookRequest1);

        //When - action or the behavior we're testing
        List<BookRequest> allBookRequests = bookRequestRepository.findAll();

        //Then - verify the output
        Assertions.assertThat(allBookRequests).isNotNull();
        Assertions.assertThat(allBookRequests.size()).isEqualTo(2);
    }

    //Junit test for get book request by id operation
    @DisplayName("Junit test for get book request by id operation")
    @Test
    public void givenBookRequestObject_whenFindById_thenReturnBookRequestObject()
    {
        //Given - precondition or setup
        bookRequestRepository.save(bookRequest);

        //When - action or the behavior we're testing
        BookRequest returnedBookRequest = bookRequestRepository.findById(bookRequest.getBookRequestId()).get();

        //Then - verify the output
        Assertions.assertThat(returnedBookRequest).isNotNull();
    }

    //Junit test for update book request operation
    @DisplayName("Junit test for update book request operation")
    @Test
    public void givenBookRequestObject_whenUpdateBookRequest_thenReturnUpdatedBookRequest()
    {
        //Given - precondition or setup
        bookRequestRepository.save(bookRequest);

        //When - action or the behavior we're testing
        RequestStatus requestStatus1 = RequestStatus.builder().requestStatus("Denied").build();

        RequestStatus savedRequestStatus1 = requestStatusRepository.save(requestStatus1);

        BookRequest savedBookRequest = bookRequestRepository.findById(bookRequest.getBookRequestId()).get();

        savedBookRequest.setRequestStatus(savedRequestStatus1);

        BookRequest updatedBookRequest = bookRequestRepository.save(savedBookRequest);

        //Then - verify the output
        Assertions.assertThat(updatedBookRequest.getRequestStatus().getRequestStatus()).isEqualTo("Denied");
    }

    //Junit test for delete book request operation
    @DisplayName("Junit test for delete book request operation")
    @Test
    public void givenBookRequestObject_whenDeleteById_thenRemoveBookRequestObject()
    {
        //Given - precondition or setup
        bookRequestRepository.save(bookRequest);

        //When - action or the behavior we're testing
        bookRequestRepository.deleteById(bookRequest.getBookRequestId());

        Optional<BookRequest> optionalBookRequest = bookRequestRepository.findById(bookRequest.getBookRequestId());

        //Then - verify the output
        Assertions.assertThat(optionalBookRequest).isEmpty();
    }

}
