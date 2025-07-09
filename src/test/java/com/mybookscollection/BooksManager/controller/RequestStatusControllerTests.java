package com.mybookscollection.BooksManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybookscollection.BooksManager.dto.RequestStatusDto;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;

@WebMvcTest
public class RequestStatusControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RequestStatusService requestStatusService;

    @MockitoBean
    private BookCategoryService bookCategoryService;

    @MockitoBean
    private BookConditionService bookConditionService;

    @MockitoBean
    private BookRequestService bookRequestService;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private CountryServie countryServie;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    //Junit test for createRequestStatus REST API
    @DisplayName("Junit test for createRequestStatus REST API")
    @Test
    public void givenRequestStatusDtoObject_whenCreateRequestStatus_thenReturnSavedRequestStatusDtoObject() throws Exception{

        //given - precondition or setup
        RequestStatusDto requestStatusDto = RequestStatusDto.builder()
                                            .requestStatus("Pending").build();

        /* Stubbing - configure response for the method */
        given(requestStatusService.createRequestStatus(ArgumentMatchers.any(RequestStatusDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(post("/booksman/api/request-statuses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestStatusDto)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.requestStatus", is(requestStatusDto.getRequestStatus())));
    }

    //Junit test for getAllRequestStatuses REST API - positive scenario
    @DisplayName("Junit test for getAllRequestStatuses REST API - positive scenario")
    @Test
    public void givenRequestStatusDtoList_whenGetAllRequestStatuses_thenReturnRequestStatusDtoList() throws Exception{

        //given - precondition or setup
        List<RequestStatusDto> allRequestStatuses = new ArrayList<>();
        allRequestStatuses.add(RequestStatusDto.builder()
                            .requestStatus("Approved").build());

        allRequestStatuses.add(RequestStatusDto.builder()
                            .requestStatus("Denied").build());

        /* Stubbing - configure response for the method */
        given(requestStatusService.getAllRequestStatuses()).willReturn(allRequestStatuses);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/request-statuses"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(allRequestStatuses.size())));
    }

    //Junit test for getAllRequestStatuses - negative scenario
    @DisplayName("Junit test for getAllRequestStatuses - negative scenario")
    @Test
    public void givenEmptyRequestStatusDtoList_whenGetAllRequestStatuses_thenReturnEmptyRequestStatusDtoList() throws Exception{

        //given - precondition or setup
        List<RequestStatusDto> allRequestStatuses = new ArrayList<>();

        /* Stubbing - configure response for the method */
        given(requestStatusService.getAllRequestStatuses()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/request-statuses"));

        //then - verify the output
        response.andDo(print())
                .andExpect(jsonPath("$.size()",is(allRequestStatuses.size())));
    }

    //Junit test for getRequestStatusById REST API - positive scenario
    @DisplayName("Junit test for getRequestStatusById REST API - positive scenario")
    @Test
    public void givenRequestStatusId_whenGetRequestStatusById_thenReturnRequestStatusDtoObject() throws Exception{

        //given - precondition or setup
        Long requestStatusId = 1L;
        RequestStatusDto requestStatusDto = RequestStatusDto.builder()
                                            .requestStatus("Pending").build();

        /* Stubbing - configure response for the method */
        given(requestStatusService.getRequestStatusById(requestStatusId)).willReturn(requestStatusDto);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/request-statuses/{id}",requestStatusId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.requestStatus",is(requestStatusDto.getRequestStatus())));
    }

    //Junit test for getRequestStatusById REST API - negative scenario
    @DisplayName("Junit test for getRequestStatusById REST API - negative scenario")
    @Test
    public void givenInvalidRequestStatusId_whenGetRequestStatusById_thenReturnResourceNotFoundException() throws Exception{

        //given - precondition or setup
        Long requestStatusId = 1L;

        /* Stubbing - configure response for the method */
        given(requestStatusService.getRequestStatusById(requestStatusId)).willThrow(new ResourceNotFoundException("Request Status", "requestStatusId", requestStatusId));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/request-statuses/{id}", requestStatusId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Junit test for updateRequestStatus REST API
    @DisplayName("Junit test for updateRequestStatus REST API")
    @Test
    public void givenUpdatedRequestStatusDtoObject_whenUpdateRequestStatus_thenReturnUpdatedRequestStatusDtoObject() throws Exception{

        //given - precondition or setup
        Long requestStatusId = 1L;

        RequestStatusDto savedRequestStatusDto = RequestStatusDto.builder()
                                                .requestStatus("Approved").build();

        RequestStatusDto updatedRequestStatusDto = RequestStatusDto.builder()
                                                .requestStatus("Denied").build();

        /* Stubbing - configure response for the method */
        given(requestStatusService.getRequestStatusById(requestStatusId)).willReturn(savedRequestStatusDto);

        /* Stubbing - configure response for the method */
        given(requestStatusService.updateRequestStatus(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(RequestStatusDto.class)))
                .willAnswer((invocation)->invocation.getArgument(1));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(put("/booksman/api/request-statuses/{id}", requestStatusId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRequestStatusDto)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.requestStatus", is(updatedRequestStatusDto.getRequestStatus())));
    }

    //Junit test for deleteRequestStatus REST API
    @DisplayName("Junit test for deleteRequestStatus REST API")
    @Test
    public void givenRequestStatusId_whenDeleteRequestStatus_thenReturn200() throws Exception{

        //given - precondition or setup
        Long requestStatusId = 1L;

        /* Stubbing - using Mockito willDoNothing API for method with void */
        willDoNothing().given(requestStatusService).deleteRequestStatus(requestStatusId);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(delete("/booksman/api/request-statuses/{id}", requestStatusId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
