package com.mybookscollection.BooksManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybookscollection.BooksManager.dto.CountryDto;
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
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.mockito.BDDMockito.given;

@WebMvcTest
public class CountryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean //@MockBean is deprecated
    private CountryServie countryService;

    /* All other services also need to be included as below, to avoid errors */
    @MockitoBean
    private BookCategoryService bookCategoryService;

    @MockitoBean
    private BookConditionService bookConditionService;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private RequestStatusService requestStatusService;

    @MockitoBean
    private BookRequestService bookRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    //Junit test for createCountry REST API
    @DisplayName("Junit test for createCountry REST API")
    @Test
    public void givenCountryDtoObject_whenCreateCountry_thenReturnSavedCountryDtoObject() throws Exception {

        //given - precondition or setup
        CountryDto countryDto = CountryDto.builder().
                                country("Canada").build();

        /*Stubbing - configure a response for the method */
        given(countryService.createCountry(ArgumentMatchers.any(CountryDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0)); //This will return whatever the argument we passed to createCountry method

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/booksman/api/countries") //URI always needs to start with '/' to avoid error 'java.lang.IllegalArgumentException:
                .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(countryDto)));//Convert object to JSON

        //then - verify the output
        response.andDo(MockMvcResultHandlers.print()) //To print the response
                .andExpect(MockMvcResultMatchers.status().isCreated())//To check status code 201 CREATED
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(countryDto.getCountry())));// Check actual value with expected value
    }

    //Junit test for getAllCountries REST API - positive scenario
    @DisplayName("Junit test for getAllCountries REST API - positive scenario ")
    @Test
    public void givenCountryDtoList_whenGetAllCountries_thenReturnCountryDtoList() throws Exception
    {
        //Given - precondition or setup
        List<CountryDto> listOfCountries = new ArrayList<>();
        listOfCountries.add(CountryDto.builder()
                .country("Canada").build());
        listOfCountries.add(CountryDto.builder()
                .country("Sri Lanka").build());

        /* Stubbing - Configure response for the method */
        given(countryService.getAllCountries()).willReturn(listOfCountries); //This will return above created list of countries

        //When - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/booksman/api/countries"));

        //Then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk()) //To check status code is 200 OK
                .andDo(MockMvcResultHandlers.print()) //To print the response
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfCountries.size())));// Check actual JSON array size with expected array size
    }

    //Junit test for getAllCountries REST API - negative scenario
    @DisplayName("Junit test for getAllCountries REST API - negative scenario")
    @Test
    public void givenEmptyCountryDtoList_whenGetAllCountries_thenReturnEmptyCountryDtoList() throws Exception{

        //given - precondition or setup
        List<CountryDto> countries = new ArrayList<>();

        /* Stubbing - configure response for the method */
        given(countryService.getAllCountries()).willReturn(Collections.emptyList()); //This will return empty list

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/booksman/api/countries"));

        //then - verify the output
        response.andDo(MockMvcResultHandlers.print()) //To print the response
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(countries.size()))); // Check actual JSON array size with expected array size (0 in this case)
    }

    //Junit test for getCountryById REST API - positive scenario
    @DisplayName("Junit test for getCountryById REST API - positive scenario")
    @Test
    public void givenCountryId_whenGetCountryById_thenReturnCountryDtoObject() throws Exception{

        //given - precondition or setup
        Long countryId = 1L;
        CountryDto countryDto = CountryDto.builder()
                                .country("Canada").build();

        /* Stubbing - configure response for the method */
        given(countryService.getCountryById(countryId)).willReturn(countryDto); //This will return CountryDto object we created above

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/booksman/api/countries/{id}", countryId));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk()) //To check status code is 200 OK
                .andDo(MockMvcResultHandlers.print()) //To print the response
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(countryDto.getCountry()))); // Check actual value with expected value
    }

    //Junit test for getCountryById REST API - negative scenario
    @DisplayName("Junit test for getCountryById REST API - negative scenario")
    @Test
    public void givenInvalidCountryId_whenGetCountryById_thenReturnResourceNotFoundException() throws Exception{

        //given - precondition or setup
        Long countryId = 1L;

        /* Stubbing - configure response for the method */
        given(countryService.getCountryById(countryId)).willThrow(new ResourceNotFoundException("Country", "countryId", countryId)); //This will return ResourceNotFound object

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/booksman/api/countries/{id}", countryId));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound()) //To check status code is 404 NOT FOUND
                .andDo(MockMvcResultHandlers.print()); //To print the response
    }

    //Junit test for updateCountry REST API
    @DisplayName("Junit test for updateCountry REST API")
    @Test
    public void givenCountryDtoObject_whenUpdateCountry_thenReturnUpdatedCountryDtoObject() throws Exception{

        //given - precondition or setup
        Long countryId = 1L;
        CountryDto savedCountryDto = CountryDto.builder()
                                .country("Sri Lanka").build();

        CountryDto updatedCountryDto = CountryDto.builder()
                                .country("Canada").build();

        /* Stubbing - configure response for the method */
        given(countryService.getCountryById(countryId)).willReturn(savedCountryDto);

        /* Stubbing - configure response for the method */
        given(countryService.updateCountry(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(CountryDto.class))) //first argument represent countryId
                .willAnswer((invocation)->invocation.getArgument(1));//This will return the 2nd argument we passed to updateCountry method

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/booksman/api/countries/{id}",countryId)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCountryDto))); //Convert object to JSON

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk()) //To check status code is 200 OK
                .andDo(MockMvcResultHandlers.print()) //To print the response
                .andExpect(MockMvcResultMatchers.jsonPath("$.country",CoreMatchers.is(updatedCountryDto.getCountry()))); // Check actual value with expected value
    }

    //Junit test for deleteCountry REST API
    @DisplayName("Junit test for deleteCountry REST API")
    @Test
    public void givenCountryId_whenDeleteCountry_thenReturn200() throws Exception{

        //given - precondition or setup
        Long countryId = 1L;

        /* Stubbing - using Mockito API willDoNothing for method with void */
        willDoNothing().given(countryService).deleteCountry(countryId);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/booksman/api/countries/{id}", countryId));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk()) //To check status code is 200 OK
                .andDo(MockMvcResultHandlers.print()); //To print the response
    }
}
