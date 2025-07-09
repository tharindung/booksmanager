package com.mybookscollection.BooksManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybookscollection.BooksManager.dto.BookCategoryDto;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;

@WebMvcTest
public class BookCategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookCategoryService bookCategoryService;

    @MockitoBean
    private BookConditionService bookConditionService;

    @MockitoBean
    private BookRequestService bookRequestService;

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

    //Junit test for createBookCategory REST API
    @DisplayName("Junit test for createBookCategory REST API")
    @Test
    public void givenBookCategoryDtoObject_whenCreateBookCategory_thenReturnSavedBookCategoryDtoObject() throws Exception{

        //given - precondition or setup
        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                                            .bookCategory("Children").build();

        /* Stubbing - configure response for the method */
        given(bookCategoryService.createBookCategory(ArgumentMatchers.any(BookCategoryDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(post("/booksman/api/book-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookCategoryDto)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookCategory", is(bookCategoryDto.getBookCategory())));
    }

    //Junit test for getAllBookCategories REST API - positive scenario
    @DisplayName("Junit test for getAllBookCategories REST API - positive scenario")
    @Test
    public void givenBookCategoryDtoList_whenGetAllBookCategories_thenReturnBookCategoryDtoList() throws Exception{

        //given - precondition or setup
        List<BookCategoryDto> listOfBookCategories = new ArrayList<>();
        listOfBookCategories.add(BookCategoryDto.builder()
                                .bookCategory("Fiction").build());
        listOfBookCategories.add(BookCategoryDto.builder()
                                .bookCategory("Sports").build());

        /* Stubbing - configure response for the method */
        given(bookCategoryService.getAllBookCategories()).willReturn(listOfBookCategories);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/book-categories"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfBookCategories.size())));
    }

    //Junit test for getAllBookCategories REST API - negative scenario
    @DisplayName("Junit test for getAllBookCategories REST API - negative scenario")
    @Test
    public void givenEmptyBookCategoryDtoList_whenGetAllBookCategories_thenReturnEmptyBookCategoryDtoList() throws Exception{

        //given - precondition or setup
        List<BookCategoryDto> listOfBookCategories = new ArrayList<>();

        /* Stubbing - configure response for the method */
        given(bookCategoryService.getAllBookCategories()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/book-categories"));

        //then - verify the output
        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfBookCategories.size())));
    }

    //Junit test for getBookCategoryById REST API - positive scenario
    @DisplayName("Junit test for getBookCategoryById - negative scenario")
    @Test
    public void givenBookCategoryId_whenGetBookCategoryById_thenReturnBookCategoryDtoObject() throws Exception{

        //given - precondition or setup
        Long bookCategoryId = 1L;
        BookCategoryDto bookCategoryDto = BookCategoryDto.builder()
                                            .bookCategory("Sports").build();

        /* Stubbing - configure response for the method */
        given(bookCategoryService.getBookCategoryById(bookCategoryId)).willReturn(bookCategoryDto);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/book-categories/{id}", bookCategoryId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.bookCategory",is(bookCategoryDto.getBookCategory())));
    }

    //Junit test for getBookCategoryById REST API - negative scenario
    @DisplayName("Junit test for getBookCategoryById - negative scenario")
    @Test
    public void givenInvalidBookCategoryId_whenGetBookCategoryById_thenReturnResourceNotFoundException() throws Exception{

        //given - precondition or setup
        Long bookCategoryId = 1L;

        /* Stubbing - configure response for the method */
        given(bookCategoryService.getBookCategoryById(bookCategoryId)).willThrow(new ResourceNotFoundException("Book Category", "bookCategoryId", bookCategoryId));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/book-categories/{id}", bookCategoryId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Junit test for updateBookCategory REST API
    @DisplayName("Junit test for updateBookCategory REST API")
    @Test
    public void givenUpdatedBookCategoryDtoObject_whenUpdateBookCategory_thenReturnUpdatedBookCategoryDtoObject() throws Exception{

        //given - precondition or setup
        Long bookCategoryId = 1L;
        BookCategoryDto savedBookCategoryDto = BookCategoryDto.builder()
                                                .bookCategory("Fiction").build();

        BookCategoryDto updatedBookCategoryDto = BookCategoryDto.builder()
                                                .bookCategory("Children").build();

        /* Stubbing - configure response for the method */
        given(bookCategoryService.getBookCategoryById(bookCategoryId)).willReturn(savedBookCategoryDto);

        /* Stubbing - configure response for the method */
        given(bookCategoryService.updateBookCategory(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(BookCategoryDto.class)))
                .willAnswer((invocation)->invocation.getArgument(1));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(put("/booksman/api/book-categories/{id}", bookCategoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBookCategoryDto)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.bookCategory", is(updatedBookCategoryDto.getBookCategory())));
    }

    //Junit test for deleteBookCategory REST API
    @DisplayName("Junit test for deleteBookCategory REST API")
    @Test
    public void givenBookCategoryId_whenDeleteBookCategory_thenReturn200() throws Exception{

        //given - precondition or setup
        Long bookCategoryId = 1L;

        /* Stubbing - using Mockito API willDoNothing for method with void */
        willDoNothing().given(bookCategoryService).deleteBookCategory(bookCategoryId);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(delete("/booksman/api/book-categories/{id}", bookCategoryId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
