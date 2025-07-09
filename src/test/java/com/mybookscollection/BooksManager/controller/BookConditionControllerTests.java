package com.mybookscollection.BooksManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybookscollection.BooksManager.dto.BookConditionDto;
import com.mybookscollection.BooksManager.exception.ResourceNotFoundException;
import com.mybookscollection.BooksManager.service.*;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.*;

@WebMvcTest
public class BookConditionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookConditionService bookConditionService;

    @MockitoBean
    private BookCategoryService bookCategoryService;

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


    //Junit test for createBookCondition REST API
    @DisplayName("Junit test for createBookCondition REST API")
    @Test
    public void givenBookConditionDtoObject_whenCreateBookCondition_thenReturnSavedBookConditionDtoObject() throws Exception{

        //given - precondition or setup
        BookConditionDto bookConditionDto = BookConditionDto.builder()
                                            .bookCondition("New").build();

        /* Stubbing - configure response for the method */
        given(bookConditionService.createBookCondition(ArgumentMatchers.any(BookConditionDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/booksman/api/book-conditions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookConditionDto)));

        //then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookCondition", CoreMatchers.is(bookConditionDto.getBookCondition())));
    }

    //Junit test for getAllBookConditions REST API - positive scenario
    @DisplayName("Junit test for getAllBookConditions REST API - positive scenario")
    @Test
    public void givenBookConditionDtoList_whenGetAllBookConditions_thenReturnBookConditionDtoList() throws Exception{

        //given - precondition or setup
        List<BookConditionDto> listOfBookConditions = new ArrayList<>();
        listOfBookConditions.add(BookConditionDto.builder()
                            .bookCondition("Old").build());
        listOfBookConditions.add(BookConditionDto.builder()
                            .bookCondition("Usable").build());

        /* Stubbing - configure response for the method */
        given(bookConditionService.getAllBookConditions()).willReturn(listOfBookConditions);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/booksman/api/book-conditions"));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfBookConditions.size())));
    }

    //Junit test for getAllBookConditions REST API - negative scenario
    @DisplayName("Junit test for getAllBookConditions REST API - negative scenario")
    @Test
    public void givenEmptyBookConditionDtoList_whenGetAllBookConditions_thenReturnEmptyBookConditionDtoList() throws Exception{

        //given - precondition or setup
        List<BookConditionDto> listOfBookConditions = new ArrayList<>();

        /* Stubbing - configure response for the method */
        given(bookConditionService.getAllBookConditions()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/booksman/api/book-conditions"));

        //then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(listOfBookConditions.size())));
    }

    //Junit test for getBookConditionById REST API - positive scenario
    @DisplayName("Junit test for getBookConditionById REST API - positive scenario")
    @Test
    public void givenBookConditionId_whenGetBookConditionById_thenReturnBookConditionDtoObject() throws Exception{

        //given - precondition or setup
        Long bookConditionId = 1L;
        BookConditionDto bookConditionDto = BookConditionDto.builder()
                                            .bookCondition("Worn Out").build();

        /* Stubbing - configure response for the method */
        given(bookConditionService.getBookConditionById(bookConditionId)).willReturn(bookConditionDto);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/booksman/api/book-conditions/{id}",bookConditionId));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookCondition", CoreMatchers.is(bookConditionDto.getBookCondition())));
    }

    //Junit test for getBookConditionById REST API - negative scenario
    @DisplayName("Junit test for getBookConditionById REST API - negative scenario")
    @Test
    public void givenInvalidBookConditionId_whenGetBookConditionById_thenReturnResourceNotFoundException() throws Exception{

        //given - precondition or setup
        Long bookConditionId = 1L;

        /* Stubbing - configure response for the method */
        given(bookConditionService.getBookConditionById(bookConditionId)).willThrow(new ResourceNotFoundException("Book Condition", "bookConditionId", bookConditionId));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/booksman/api/book-conditions/{id}", bookConditionId));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    //Junit test for updateBookCondition REST API
    @DisplayName("Junit test for updateBookCondition REST API")
    @Test
    public void givenUpdatedBookConditionDtoObject_whenUpdateBookCondition_thenReturnUpdatedBookConditionDtoObject() throws Exception{

        //given - precondition or setup
        Long bookConditionId = 1L;
        BookConditionDto savedBookConditionDto = BookConditionDto.builder()
                                                .bookCondition("Science Fiction").build();

        BookConditionDto updatedBookConditionDto = BookConditionDto.builder()
                                                .bookCondition("Children").build();

        /* Stubbing - configure response for the method */
        given(bookConditionService.getBookConditionById(bookConditionId)).willReturn(savedBookConditionDto);

        /* Stubbing - configure response for the method */
        given(bookConditionService.updateBookCondition(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(BookConditionDto.class)))
                .willAnswer((invocation)->invocation.getArgument(1));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/booksman/api/book-conditions/{id}", bookConditionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBookConditionDto)));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookCondition", CoreMatchers.is(updatedBookConditionDto.getBookCondition())));
    }

    //Junit test for deleteBookCondition REST API
    @DisplayName("Junit test for deleteBookCondition REST API")
    @Test
    public void givenBookConditionId_whenDeleteBookCondition_thenReturn200() throws Exception{

        //given - precondition or setup
        Long bookConditionId = 1L;

        /* Stubbing - using Mockito API willDoNothing for method with void */
        willDoNothing().given(bookConditionService).deleteBookCondition(bookConditionId);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/booksman/api/book-conditions/{id}", bookConditionId));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
