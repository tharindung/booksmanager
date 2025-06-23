package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.CountryDto;
import com.mybookscollection.BooksManager.dto.UserDisplayDto;
import com.mybookscollection.BooksManager.dto.UserDto;
import com.mybookscollection.BooksManager.entity.Country;
import com.mybookscollection.BooksManager.entity.User;
import com.mybookscollection.BooksManager.repository.UserRepository;
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
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserDto userDto;

    private UserDisplayDto userDisplayDto;

    private Country country;

    private CountryDto countryDto;

    @BeforeEach
    public void Setup()
    {
        country = Country.builder().countryId(1L).country("Canada").build();

        countryDto = CountryDto.builder().countryId(1L).country("Canada").build();

        user = User.builder().userId(1L).userName("John Doe").userEmail("john.doe@gmail.com").userPassword("12345").userCountry(country).build();

        userDto = UserDto.builder().userId(1L).userName("John Doe").userEmail("john.doe@gmail.com").userPassword("12345").userCountry(countryDto).build();

        userDisplayDto = UserDisplayDto.builder().userId(1L).userName("John Doe").userEmail("john.doe@gmail.com").userCountry(countryDto).build();
    }

    //Junit test for createUser method
    @DisplayName("Junit test for createUser method")
    @Test
    public void givenUserDtoObject_whenCreateUser_thenReturnSavedUserDisplayDtoObject(){

        //given - precondition or setup
        given(userRepository.save(user)).willReturn(user);

        doReturn(userDisplayDto).when(modelMapper).map(user, UserDisplayDto.class);
        doReturn(user).when(modelMapper).map(userDto, User.class);

        //when - action or the behavior we're testing
        UserDisplayDto savedUser = userService.createUser(userDto);

        //then - verify the output
        Assertions.assertThat(savedUser).isNotNull();
    }

    //Junit test for getAllUsers method positive scenario
    @DisplayName("Junit test for getAllUsers method positive scenario")
    @Test
    public void givenUserList_whenGetAllUsers_thenReturnUserDisplayDtoList(){

        //given - precondition or setup
        User user1 = User.builder().userId(2L).userName("Jane Doe").userEmail("jane.doe@gmail.com").userPassword("12345").userCountry(country).build();

        given(userRepository.findAll()).willReturn(List.of(user, user1));

        //when - action or the behavior we're testing
        List<UserDisplayDto> allUsers = userService.getAllUsers();

        //then - verify the output
        Assertions.assertThat(allUsers).isNotNull();
        Assertions.assertThat(allUsers.size()).isEqualTo(2);
    }

    //Junit test for getAllUsers negative scenario
    @DisplayName("Junit test for getAllUsers negative scenario")
    @Test
    public void givenEmptyUserList_whenGetAllUsers_thenReturnEmptyUserDisplayDtoList(){

        //given - precondition or setup
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behavior we're testing
        List<UserDisplayDto> allUsers = userService.getAllUsers();

        //then - verify the output
        Assertions.assertThat(allUsers).isEmpty();
        Assertions.assertThat(allUsers.size()).isEqualTo(0);
    }

    //Junit test for getUserById method
    @DisplayName("Junit test for getUserById method")
    @Test
    public void givenUserId_whenGetUserById_thenReturnUserDisplayDtoObject(){

        //given - precondition or setup
        given(userRepository.findById(user.getUserId())).willReturn(Optional.of(user));

        doReturn(userDisplayDto).when(modelMapper).map(user, UserDisplayDto.class);

        //when - action or the behavior we're testing
        UserDisplayDto returnedUser = userService.getUserById(user.getUserId());

        //then - verify the output
        Assertions.assertThat(returnedUser).isNotNull();
    }

    //Junit test for updateUser method
    @DisplayName("Junit test for updateUser method")
    @Test
    public void givenUserDtoObject_whenUpdateUser_thenReturnUpdatedUserDisplayDtoObject(){

        //given - precondition or setup
        given(userRepository.findById(user.getUserId())).willReturn(Optional.of(user));

        given(userRepository.save(user)).willReturn(user);

        doReturn(userDisplayDto).when(modelMapper).map(user, UserDisplayDto.class);

        doReturn(user.getUserCountry()).when(modelMapper).map(userDisplayDto.getUserCountry(), Country.class);

        user.setUserName("Mark Smith");
        user.setUserEmail("mark.smith@gmail.com");

        userDto.setUserName("Mark Smith");
        userDto.setUserEmail("mark.smith@gmail.com");

        userDisplayDto.setUserName("Mark Smith");
        userDisplayDto.setUserEmail("mark.smith@gmail.com");

        //when - action or the behavior we're testing
        UserDisplayDto updatedUser = userService.updateUser(userDto.getUserId(), userDto);

        //then - verify the output
        Assertions.assertThat(updatedUser.getUserName()).isEqualTo("Mark Smith");
        Assertions.assertThat(updatedUser.getUserEmail()).isEqualTo("mark.smith@gmail.com");
    }

    //Junit test for deleteUser method
    @DisplayName("Junit test for deleteUser method")
    @Test
    public void givenUserId_whenDeleteUser_thenReturnNothing(){

        //given - precondition or setup
        given(userRepository.findById(user.getUserId())).willReturn(Optional.of(user));

        willDoNothing().given(userRepository).deleteById(user.getUserId());

        //when - action or the behavior we're testing
        userService.deleteUser(userDisplayDto.getUserId());

        //then - verify the output
        verify(userRepository, times(1)).deleteById(userDisplayDto.getUserId());
    }
}
