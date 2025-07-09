package com.mybookscollection.BooksManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybookscollection.BooksManager.dto.*;
import com.mybookscollection.BooksManager.exception.ResourceNotFoundException;
import com.mybookscollection.BooksManager.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;

@WebMvcTest
public class BookRequestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookRequestService bookRequestService;

    @MockitoBean
    private BookCategoryService bookCategoryService;

    @MockitoBean
    private BookConditionService bookConditionService;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private CountryServie countryService;

    @MockitoBean
    private RequestStatusService requestStatusService;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    //Junit test for createBookRequest REST API
    @DisplayName("Junit test for createBookRequest REST API")
    @Test
    public void givenBookRequestDtoObject_whenCreateBookRequest_thenReturnSavedBookRequestDtoObject() throws Exception{

        //given - precondition or setup
        CountryDto countryDto = CountryDto.builder().
                country("Canada").build();

        UserDisplayDto requestedUserDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        RequestStatusDto requestStatusDto = RequestStatusDto.builder()
                .requestStatus("Pending").build();

        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                .bookCategory("Children").build();

        BookConditionDto bookConditionDto = BookConditionDto.builder()
                .bookCondition("Used").build();

        UserDisplayDto bookOwnerDto = UserDisplayDto.builder()
                .userName("Jane Doe")
                .userEmail("jane.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        BookDto bookDto = BookDto.builder()
                .bookName("Harry Potter")
                .bookAuthor("J.K.Rowling")
                .bookShareable(true)
                .bookCategory(bookCategoryDto)
                .bookCondition(bookConditionDto)
                .bookOwner(bookOwnerDto).build();

        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .book(bookDto)
                .requestStatus(requestStatusDto)
                .requestedUser(requestedUserDto).build();

        /* Stubbing - configure response for the method */
        given(bookRequestService.createBookRequest(ArgumentMatchers.any(BookRequestDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(post("/booksman/api/book-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequestDto)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.book.bookName", is(bookRequestDto.getBook().getBookName())))
                .andExpect(jsonPath("$.requestStatus.requestStatus", is(bookRequestDto.getRequestStatus().getRequestStatus())))
                .andExpect(jsonPath("$.requestedUser.userName",is(bookRequestDto.getRequestedUser().getUserName())));
    }

    //Junit test for getAllBookRequests REST API - positive scenario
    @DisplayName("Junit test for getAllBookRequests REST API - positive scenario")
    @Test
    public void givenBookRequestDtoList_whenGetAllBookRequests_thenReturnBookRequestDtoList() throws Exception{

        //given - precondition or setup
        CountryDto countryDto = CountryDto.builder().
                country("Canada").build();

        UserDisplayDto requestedUserDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        UserDisplayDto requestedUserDto1 = UserDisplayDto.builder()
                .userName("Mark Smith")
                .userEmail("mark.smith@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-03"))
                .userCountry(countryDto).build();

        RequestStatusDto requestStatusDto = RequestStatusDto.builder()
                .requestStatus("Pending").build();

        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                .bookCategory("Children").build();

        BookConditionDto bookConditionDto = BookConditionDto.builder()
                .bookCondition("Used").build();

        UserDisplayDto bookOwnerDto = UserDisplayDto.builder()
                .userName("Jane Doe")
                .userEmail("jane.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        BookDto bookDto = BookDto.builder()
                .bookName("Harry Potter")
                .bookAuthor("J.K.Rowling")
                .bookShareable(true)
                .bookCategory(bookCategoryDto)
                .bookCondition(bookConditionDto)
                .bookOwner(bookOwnerDto).build();

        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .book(bookDto)
                .requestStatus(requestStatusDto)
                .requestedUser(requestedUserDto).build();

        BookRequestDto bookRequestDto1 = BookRequestDto.builder()
                .book(bookDto)
                .requestStatus(requestStatusDto)
                .requestedUser(requestedUserDto1).build();

        List<BookRequestDto> bookRequestDtoList = new ArrayList<>();
        bookRequestDtoList.add(bookRequestDto);
        bookRequestDtoList.add(bookRequestDto1);

        /* Stubbing - configure response for the method */
        given(bookRequestService.getAllBookRequests()).willReturn(bookRequestDtoList);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/book-requests"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(bookRequestDtoList.size())));
    }

    //Junit test for getAllBookRequests REST API - negative scenario
    @DisplayName("Junit test for getAllBookRequests REST API - negative scenario")
    @Test
    public void givenEmptyBookRequestDtoList_whenGetAllBookRequests_thenReturnEmptyBookRequestDtoList() throws Exception{

        //given - precondition or setup
        List<BookRequestDto> allBookRequests = new ArrayList<>();

        /* Stubbing - configure response for the method */
        given(bookRequestService.getAllBookRequests()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/book-requests"));

        //then - verify the output
        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(allBookRequests.size())));
    }

    //Junit test for getBookRequestById REST API - positive scenario
    @DisplayName("Junit test for getBookRequestById REST API - positive scenario")
    @Test
    public void givenBookRequestId_whenGetBookRequestById_thenBookRequestDtoObject() throws Exception{

        //given - precondition or setup
        Long bookRequestId = 1L;

        CountryDto countryDto = CountryDto.builder().
                country("Canada").build();

        UserDisplayDto requestedUserDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        RequestStatusDto requestStatusDto = RequestStatusDto.builder()
                .requestStatus("Pending").build();

        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                .bookCategory("Children").build();

        BookConditionDto bookConditionDto = BookConditionDto.builder()
                .bookCondition("Used").build();

        UserDisplayDto bookOwnerDto = UserDisplayDto.builder()
                .userName("Jane Doe")
                .userEmail("jane.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        BookDto bookDto = BookDto.builder()
                .bookName("Harry Potter")
                .bookAuthor("J.K.Rowling")
                .bookShareable(true)
                .bookCategory(bookCategoryDto)
                .bookCondition(bookConditionDto)
                .bookOwner(bookOwnerDto).build();

        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .book(bookDto)
                .requestStatus(requestStatusDto)
                .requestedUser(requestedUserDto).build();

        /* Stubbing - configure response for the method */
        given(bookRequestService.getBookRequestById(bookRequestId)).willReturn(bookRequestDto);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/book-requests/{id}", bookRequestId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.book.bookName", is(bookRequestDto.getBook().getBookName())))
                .andExpect(jsonPath("$.requestStatus.requestStatus", is(bookRequestDto.getRequestStatus().getRequestStatus())))
                .andExpect(jsonPath("$.requestedUser.userName",is(bookRequestDto.getRequestedUser().getUserName())));
    }

    //Junit test for getBookRequestById REST API - negative scenario
    @DisplayName("Junit test for getBookRequestById REST API - negative scenario")
    @Test
    public void givenInvalidBookRequestId_whenGetBookRequestById_thenThrowResourceNotFoundException() throws Exception{

        //given - precondition or setup
        Long bookRequestId = 1L;

        /* Stubbing -  */
        given(bookRequestService.getBookRequestById(bookRequestId)).willThrow(new ResourceNotFoundException("Book Request","bookRequestId", bookRequestId));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/book-requests/{id}",bookRequestId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Junit test for updateBookRequest REST API
    @DisplayName("Junit test for updateBookRequest REST API")
    @Test
    public void givenUpdatedBookRequestDtoObject_whenUpdateBookRequest_thenReturnUpdatedBookRequestDtoObject() throws Exception{

        //given - precondition or setup
        Long bookRequestId = 1L;

        CountryDto countryDto = CountryDto.builder().
                country("Canada").build();

        UserDisplayDto requestedUserDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        UserDisplayDto requestedUserDto1 = UserDisplayDto.builder()
                .userName("Mark Smith")
                .userEmail("mark.smith@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-05"))
                .userCountry(countryDto).build();

        RequestStatusDto requestStatusDto = RequestStatusDto.builder()
                .requestStatus("Pending").build();

        RequestStatusDto requestStatusDto1 = RequestStatusDto.builder()
                .requestStatus("Approved").build();

        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                .bookCategory("Children").build();

        BookConditionDto bookConditionDto = BookConditionDto.builder()
                .bookCondition("Used").build();

        UserDisplayDto bookOwnerDto = UserDisplayDto.builder()
                .userName("Jane Doe")
                .userEmail("jane.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        BookDto bookDto = BookDto.builder()
                .bookName("Harry Potter")
                .bookAuthor("J.K.Rowling")
                .bookShareable(true)
                .bookCategory(bookCategoryDto)
                .bookCondition(bookConditionDto)
                .bookOwner(bookOwnerDto).build();

        BookRequestDto savedBookRequestDto = BookRequestDto.builder()
                .book(bookDto)
                .requestStatus(requestStatusDto)
                .requestedUser(requestedUserDto).build();

        BookRequestDto updatedBookRequestDto = BookRequestDto.builder()
                .book(bookDto)
                .requestStatus(requestStatusDto1)
                .requestedUser(requestedUserDto1).build();

        /* Stubbing - configure response for the method */
        given(bookRequestService.getBookRequestById(bookRequestId)).willReturn(savedBookRequestDto);

        /* Stubbing - configure response for the method */
        given(bookRequestService.updateBookRequest(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(BookRequestDto.class)))
                .willAnswer((invocation)->invocation.getArgument(1));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(put("/booksman/api/book-requests/{id}", bookRequestId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBookRequestDto)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.requestStatus.requestStatus", is(updatedBookRequestDto.getRequestStatus().getRequestStatus())))
                .andExpect(jsonPath("$.requestedUser.userName", is(updatedBookRequestDto.getRequestedUser().getUserName())));
    }

    //Junit test for deleteBookRequest REST API
    @DisplayName("Junit test for deleteBookRequest REST API")
    @Test
    public void givenBookRequestId_whenDeleteBookRequest_thenReturn200() throws Exception{

        //given - precondition or setup
        Long bookRequestId = 1L;

        /* Stubbing - using Mockito API willDoNothing for method with void */
        willDoNothing().given(bookRequestService).deleteBookRequest(bookRequestId);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(delete("/booksman/api/book-requests/{id}", bookRequestId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
