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
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private BookCategoryService bookCategoryService;

    @MockitoBean
    private BookConditionService bookConditionService;

    @MockitoBean
    private BookRequestService bookRequestService;

    @MockitoBean
    private CountryServie countryServie;

    @MockitoBean
    private RequestStatusService requestStatusService;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    //Junit test for createBook REST API
    @DisplayName("Junit test for createBook REST API")
    @Test
    public void givenBookDtoObject_whenCreateBook_thenReturnSavedBookDtoObject() throws Exception{

        //given - precondition or setup
        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                                            .bookCategory("Children").build();

        BookConditionDto bookConditionDto = BookConditionDto.builder()
                                            .bookCondition("Used").build();

        CountryDto countryDto = CountryDto.builder()
                                .country("Canada").build();

        UserDisplayDto bookOwnerDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();


        BookDto bookDto = BookDto.builder()
                            .bookName("Harry Potter")
                            .bookAuthor("J.K.Rowling")
                            .bookShareable(true)
                            .bookCategory(bookCategoryDto)
                            .bookCondition(bookConditionDto)
                            .bookOwner(bookOwnerDto).build();

        /* Stubbing - configure response for the method */
        given(bookService.createBook(ArgumentMatchers.any(BookDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(post("/booksman/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookName", is(bookDto.getBookName())))
                .andExpect(jsonPath("$.bookAuthor", is(bookDto.getBookAuthor())));
    }

   //Junit test for getAllBooks REST API - positive scenario
   @DisplayName("Junit test for getAllBooks REST API - positive scenario")
   @Test
   public void givenBookDtoList_whenGetAllBooks_thenReturnBookDtoList() throws Exception{

        //given - precondition or setup
        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
               .bookCategory("Children").build();

        BookConditionDto bookConditionDto = BookConditionDto.builder()
               .bookCondition("Used").build();

        CountryDto countryDto = CountryDto.builder()
               .country("Canada").build();

        UserDisplayDto bookOwnerDto = UserDisplayDto.builder()
               .userName("John Doe")
               .userEmail("john.doe@gmail.com")
               .userJoinedDate(LocalDate.parse("2025-07-01"))
               .userCountry(countryDto).build();


        BookDto bookDto = BookDto.builder()
               .bookName("Harry Potter")
               .bookAuthor("J.K.Rowling")
               .bookShareable(true)
               .bookCategory(bookCategoryDto)
               .bookCondition(bookConditionDto)
               .bookOwner(bookOwnerDto).build();

        BookDto bookDto1 = BookDto.builder()
               .bookName("Famous Five")
               .bookAuthor("Enid Blyton")
               .bookShareable(true)
               .bookCategory(bookCategoryDto)
               .bookCondition(bookConditionDto)
               .bookOwner(bookOwnerDto).build();

        List<BookDto> allBooks = new ArrayList<>();

        allBooks.add(bookDto);
        allBooks.add(bookDto1);

        /* Stubbing - configure response for the method */
        given(bookService.getAllBooks()).willReturn(allBooks);

       //when - action or the behavior we're testing
       ResultActions response = mockMvc.perform(get("/booksman/api/books"));

       //then - verify the output
       response.andExpect(status().isOk())
               .andDo(print())
               .andExpect(jsonPath("$.size()",is(allBooks.size())));
    }

    //Junit test for getAllBooks REST API - negative scenario
    @DisplayName("Junit test for getAllBooks REST API - negative scenario")
    @Test
    public void givenEmptyBookDtoList_whenGetAllBooks_thenReturnEmptyBookDtoList() throws Exception{

        //given - precondition or setup
        List<BookDto> listOfBooks = new ArrayList<>();

        /* Stubbing - configure response for the method */
        given(bookService.getAllBooks()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/books"));

        //then - verify the output
        response.andDo(print())
                .andExpect(jsonPath("$.size()",is(listOfBooks.size())));
    }

    //Junit test for getBookById REST API - positive scenario
    @DisplayName("Junit test for getBookById REST API - positive scenario")
    @Test
    public void givenBookId_whenGetBookById_thenReturnBookDtoObject() throws Exception{

        //given - precondition or setup
        Long bookId = 1L;

        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                .bookCategory("Children").build();

        BookConditionDto bookConditionDto = BookConditionDto.builder()
                .bookCondition("Used").build();

        CountryDto countryDto = CountryDto.builder()
                .country("Canada").build();

        UserDisplayDto bookOwnerDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();


        BookDto bookDto = BookDto.builder()
                .bookName("Harry Potter")
                .bookAuthor("J.K.Rowling")
                .bookShareable(true)
                .bookCategory(bookCategoryDto)
                .bookCondition(bookConditionDto)
                .bookOwner(bookOwnerDto).build();

        /* Stubbing - configure response for the method */
        given(bookService.getBookById(bookId)).willReturn(bookDto);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/books/{id}", bookId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.bookName", is(bookDto.getBookName())))
                .andExpect(jsonPath("$.bookAuthor", is(bookDto.getBookAuthor())));
    }

    //Junit test for getBookById REST API - negative scenario
    @DisplayName("Junit test for getBookById - negative scenario")
    @Test
    public void givenInvalidBookId_whenGetBookById_thenThrowResourceNotFoundException() throws Exception{

        //given - precondition or setup
        Long bookId = 1L;

        /* Stubbing - configure response for the method */
        given(bookService.getBookById(bookId)).willThrow(new ResourceNotFoundException("Book", "bookId", bookId));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/books/{id}", bookId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Junit test for updateBook REST API
    @DisplayName("Junit test for updateBook REST API")
    @Test
    public void givenUpdatedBookDtoObject_whenUpdateBook_thenReturnUpdatedBookDtoObject() throws Exception{

        //given - precondition or setup
        Long bookId = 1L;

        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                .bookCategory("Children").build();

        BookConditionDto bookConditionDto = BookConditionDto.builder()
                .bookCondition("Used").build();

        CountryDto countryDto = CountryDto.builder()
                .country("Canada").build();

        UserDisplayDto bookOwnerDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();


        BookDto savedBookDto = BookDto.builder()
                .bookName("Harry Potter")
                .bookAuthor("J.K.Rowling")
                .bookShareable(true)
                .bookCategory(bookCategoryDto)
                .bookCondition(bookConditionDto)
                .bookOwner(bookOwnerDto).build();

        BookDto updatedBookDto = BookDto.builder()
                .bookName("Famous Five")
                .bookAuthor("Enid Blyton")
                .bookShareable(true)
                .bookCategory(bookCategoryDto)
                .bookCondition(bookConditionDto)
                .bookOwner(bookOwnerDto).build();

        /* Stubbing - configure response for the method */
        given(bookService.getBookById(bookId)).willReturn(savedBookDto);

        /* Stubbing - configure response for the method */
        given(bookService.updateBook(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(BookDto.class)))
                .willAnswer((invocation)->invocation.getArgument(1));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(put("/booksman/api/books/{id}",bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBookDto)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.bookName",is(updatedBookDto.getBookName())))
                .andExpect(jsonPath("$.bookAuthor",is(updatedBookDto.getBookAuthor())));
    }

    //Junit test for deleteBook REST API
    @DisplayName("Junit test for deleteBook REST API")
    @Test
    public void givenBookId_whenDeleteBook_thenReturn200() throws Exception{

        //given - precondition or setup
        Long bookId = 1L;

        /* Stubbing - using Mockito API willDoNothing for a method with void */
        willDoNothing().given(bookService).deleteBook(bookId);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(delete("/booksman/api/books/{id}", bookId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
