package com.mybookscollection.BooksManager.controller;

import com.mybookscollection.BooksManager.dto.UserDisplayDto;
import com.mybookscollection.BooksManager.dto.UserDto;
import com.mybookscollection.BooksManager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("booksman/api/users")
@Tag(name = "Users", description = "CRUD operations for users")
public class UserController {

    private UserService userService;

    @Operation(
            summary = "Create a new user",
            description = "Creates a user and returns the public display version of the user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDisplayDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    //public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto)
    public ResponseEntity<UserDisplayDto> createUser(@RequestBody @Valid UserDto userDto) /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    {
        //UserDto savedUser = userService.createUser(userDto);
        UserDisplayDto savedUser = userService.createUser(userDto);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all users (excluding sensitive information)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of users",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDisplayDto.class)))
            }
    )
    @GetMapping
    //public ResponseEntity<List<UserDto>> getAllUsers()
    public ResponseEntity<List<UserDisplayDto>> getAllUsers() /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    {
        //List<UserDto> allUsers = userService.getAllUsers();
        List<UserDisplayDto> allUsers = userService.getAllUsers();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @Operation(
            summary = "Get user by ID",
            description = "Returns a specific user by their ID (excluding sensitive data)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDisplayDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    //public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId)
    public ResponseEntity<UserDisplayDto> getUserById(@PathVariable("id") Long userId) /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */
    {
        //UserDto foundUser = userService.getUserById(userId);
        UserDisplayDto foundUser = userService.getUserById(userId);

        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a user",
            description = "Updates an existing user and returns the updated user (excluding password)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDisplayDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    @PutMapping("{id}")
    //public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto userDto){
    public ResponseEntity<UserDisplayDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto userDto){ /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */

        //UserDto updatedUser = userService.updateUser(userId, userDto);
        UserDisplayDto updatedUser = userService.updateUser(userId, userDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a user",
            description = "Deletes a user by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId)
    {
        userService.deleteUser(userId);

        return new ResponseEntity<>("User with ID : " + userId + " deleted successfully !", HttpStatus.OK);
    }
}
