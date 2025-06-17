package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.UserDisplayDto;
import com.mybookscollection.BooksManager.dto.UserDto;

import java.util.List;

public interface UserService {

    //UserDto createUser(UserDto userDto); /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    UserDisplayDto createUser(UserDto userDto);

    //List<UserDto> getAllUsers();
    List<UserDisplayDto> getAllUsers(); /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */

    UserDto getUserDtoById(Long userId); /* This is still needed to get the 'UserDto' object by passing userId */
    UserDisplayDto getUserById(Long userId); /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */

    //UserDto updateUser(Long userId, UserDto userDto);
    UserDisplayDto updateUser(Long userId, UserDto userDto);

    void deleteUser(Long userId);
}
