package com.mybookscollection.BooksManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybookscollection.BooksManager.dto.CountryDto;
import com.mybookscollection.BooksManager.dto.UserDisplayDto;
import com.mybookscollection.BooksManager.dto.UserDto;
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
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

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

    @Autowired
    private ObjectMapper objectMapper;

    //Junit test for createUser REST API
    @DisplayName("Junit test for createUser REST API")
    @Test
    public void givenUserDtoObject_whenCreateUser_thenReturnUserDisplayDtoObject() throws Exception{

        //given - precondition or setup
        CountryDto countryDto = CountryDto.builder()
                                .country("Canada").build();

        UserDto userDto = UserDto.builder()
                            .userName("John Doe")
                            .userEmail("john.doe@gmail.com")
                            .userPassword("12345")
                            .userJoinedDate(LocalDate.parse("2025-07-01"))
                            .userCountry(countryDto).build();

        UserDisplayDto userDisplayDto = UserDisplayDto.builder()
                                .userName("John Doe")
                                .userEmail("john.doe@gmail.com")
                                .userJoinedDate(LocalDate.parse("2025-07-01"))
                                .userCountry(countryDto).build();

        /* Stubbing - configure response for the method */
        given(userService.createUser(ArgumentMatchers.any(UserDto.class)))
                .willReturn(userDisplayDto);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(post("/booksman/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName", is(userDisplayDto.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(userDisplayDto.getUserEmail())));
    }

    //Junit test for getAllUsers REST API - positive scenario
    @DisplayName("Junit test for getAllUsers REST API - positive scenario")
    @Test
    public void givenUserDisplayDtoList_whenGetAllUsers_thenReturnUserDisplayDtoList() throws Exception{

        //given - precondition or setup
        CountryDto countryDto = CountryDto.builder()
                .country("Canada").build();

        UserDisplayDto userDisplayDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        UserDisplayDto userDisplayDto1 = UserDisplayDto.builder()
                .userName("Jane Doe")
                .userEmail("jane.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-05"))
                .userCountry(countryDto).build();

        List<UserDisplayDto> allUsers = new ArrayList<>();
        allUsers.add(userDisplayDto);
        allUsers.add(userDisplayDto1);

        /* Stubbing - configure response for the method */
        given(userService.getAllUsers()).willReturn(allUsers);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/users"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(allUsers.size())));
    }

    //Junit test for getAllUsers REST API - negative scenario
    @DisplayName("Junit test for getAllUsers REST API - negative scenario")
    @Test
    public void givenEmptyUserDisplayDtoList_whenGetAllUsers_thenReturnEmptyUserDisplayDtoList() throws Exception{

        //given - precondition or setup
        List<UserDisplayDto> listOfUsers = new ArrayList<>();

        /* Stubbing - configure response for the method */
        given(userService.getAllUsers()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/books"));

        //then - verify the output
        response.andDo(print())
                .andExpect(jsonPath("$.size()",is(listOfUsers.size())));
    }

    //Junit test for getUserById REST API - positive scenario
    @DisplayName("Junit test for getUserById REST API - positive scenario")
    @Test
    public void givenUserId_whenGetUserById_thenReturnUserDisplayDtoObject() throws Exception{

        //given - precondition or setup
        Long userId = 1L;

        CountryDto countryDto = CountryDto.builder()
                .country("Canada").build();

        UserDisplayDto userDisplayDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        /* Stubbing - configure response for the method */
        given(userService.getUserById(userId)).willReturn(userDisplayDto);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/users/{id}", userId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.userName", is(userDisplayDto.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(userDisplayDto.getUserEmail())));
    }

    //Junit test for getUserById - negative scenario
    @DisplayName("Junit test for getUserById - negative scenario")
    @Test
    public void givenInvalidUserId_whenGetUserById_thenReturnUserDisplayDtoObject() throws Exception{

        //given - precondition or setup
        Long userId = 1L;

        /* Stubbing - configure response for the method */
        given(userService.getUserById(userId)).willThrow(new ResourceNotFoundException("User", "userId", userId));

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(get("/booksman/api/users/{id}", userId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Junit test for updateUser REST API
    @DisplayName("Junit test for updateUser REST API")
    @Test
    public void givenUpdatedUserDtoObject_whenUpdateUser_thenReturnUpdatedUserDtoObject() throws Exception{

        //given - precondition or setup
        Long userId = 1L;

        CountryDto countryDto = CountryDto.builder()
                .country("Canada").build();

        UserDto savedUerDto = UserDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userPassword("12345")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        /*UserDisplayDto savedUserDisplayDto = UserDisplayDto.builder()
                .userName("John Doe")
                .userEmail("john.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();*/

        UserDto updatedUerDto = UserDto.builder()
                .userName("Jane Doe")
                .userEmail("jane.doe@gmail.com")
                .userPassword("12345")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        UserDisplayDto updatedUserDisplayDto = UserDisplayDto.builder()
                .userName("Jane Doe")
                .userEmail("jane.doe@gmail.com")
                .userJoinedDate(LocalDate.parse("2025-07-01"))
                .userCountry(countryDto).build();

        /* Stubbing - configure response for the method */
        given(userService.getUserDtoById(userId)).willReturn(savedUerDto);

        /* Stubbing - configure response for the method */
        given(userService.updateUser(ArgumentMatchers.any(Long.class),ArgumentMatchers.any(UserDto.class)))
                .willReturn(updatedUserDisplayDto);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(put("/booksman/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUerDto)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.userName",is(updatedUserDisplayDto.getUserName())))
                .andExpect(jsonPath("$.userEmail",is(updatedUserDisplayDto.getUserEmail())));
    }

    //Junit test for deleteUser REST API
    @DisplayName("Junit test for deleteUser REST API")
    @Test
    public void givenUserId_whenDeleteUser_thenReturn200() throws Exception{

        //given - precondition or setup
        Long userId = 1L;

        /* Stubbing - Using Mockito API willDoNothing for method with void */
        willDoNothing().given(userService).deleteUser(userId);

        //when - action or the behavior we're testing
        ResultActions response = mockMvc.perform(delete("/booksman/api/users/{id}",userId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
