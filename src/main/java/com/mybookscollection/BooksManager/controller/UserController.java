package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.UserDisplayDto;
import com.mybookscollection.BooksManager.dto.UserDto;
import com.mybookscollection.BooksManager.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/users")
public class UserController {

    private UserService userService;

    @PostMapping
    //public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto)
    public ResponseEntity<UserDisplayDto> createUser(@RequestBody @Valid UserDto userDto) /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    {
        //UserDto savedUser = userService.createUser(userDto);
        UserDisplayDto savedUser = userService.createUser(userDto);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    //public ResponseEntity<List<UserDto>> getAllUsers()
    public ResponseEntity<List<UserDisplayDto>> getAllUsers() /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    {
        //List<UserDto> allUsers = userService.getAllUsers();
        List<UserDisplayDto> allUsers = userService.getAllUsers();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    //public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId)
    public ResponseEntity<UserDisplayDto> getUserById(@PathVariable("id") Long userId) /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    {
        //UserDto foundUser = userService.getUserById(userId);
        UserDisplayDto foundUser = userService.getUserById(userId);

        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @PutMapping("{id}")
    //public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto userDto){
    public ResponseEntity<UserDisplayDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto userDto){ /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */

        //UserDto updatedUser = userService.updateUser(userId, userDto);
        UserDisplayDto updatedUser = userService.updateUser(userId, userDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId)
    {
        userService.deleteUser(userId);

        return new ResponseEntity<>("User with ID : " + userId + " deleted successfully !", HttpStatus.OK);
    }
}
