package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.UserDisplayDto;
import com.mybookscollection.BooksManager.dto.UserDto;
import com.mybookscollection.BooksManager.entity.Book;
import com.mybookscollection.BooksManager.entity.BookRequest;
import com.mybookscollection.BooksManager.entity.Country;
import com.mybookscollection.BooksManager.entity.User;
import com.mybookscollection.BooksManager.exception.ResourceNotFoundException;
import com.mybookscollection.BooksManager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    //public UserDto createUser(UserDto userDto) {
    public UserDisplayDto createUser(UserDto userDto) { /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */

        User newUser = modelMapper.map(userDto, User.class);

        User savedUser = userRepository.save(newUser);

        //return modelMapper.map(savedUser, UserDto.class);
        return modelMapper.map(savedUser, UserDisplayDto.class);
    }

    @Override
    //public List<UserDto> getAllUsers() {
    public List<UserDisplayDto> getAllUsers() { /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */

        List<User> allUsers = userRepository.findAll();

        //return allUsers.stream().map((u)->modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
        return allUsers.stream().map((u)->modelMapper.map(u, UserDisplayDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserDtoById(Long userId) { /* This method is required to return a 'UserDto' object by ID */

        User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

        return modelMapper.map(foundUser, UserDto.class);
    }

    @Override
    //public UserDto getUserById(Long userId) {
    public UserDisplayDto getUserById(Long userId) { /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */

        //User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID : " + userId + " does not exist !"));
        User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

        //return modelMapper.map(foundUser, UserDto.class);
        return modelMapper.map(foundUser, UserDisplayDto.class);
    }

    @Override
    //public UserDto updateUser(Long userId, UserDto userDto) {
    public UserDisplayDto updateUser(Long userId, UserDto userDto) { /* Using 'UserDisplayDto' instead of 'UserDto' to mask sensitive information */

        //User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID : " + userId + " does not exist !"));
        User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

        foundUser.setUserName(userDto.getUserName());
        foundUser.setUserEmail(userDto.getUserEmail());
        foundUser.setUserPassword(userDto.getUserPassword());
        //foundUser.setUserCountry(userDto.getUserCountry());
        foundUser.setUserCountry(modelMapper.map(userDto.getUserCountry(), Country.class));
        foundUser.setUserJoinedDate(userDto.getUserJoinedDate());
        //foundUser.setBooks(userDto.getBooks());

        /* Before access 'stream()' method we need to make sure books set is not null  */
        if(userDto.getBooks() != null)
        {
            foundUser.setBooks(userDto.getBooks().stream().map(b->modelMapper.map(b, Book.class)).collect(Collectors.toSet()));
        }
        else
        {
            foundUser.setBooks(null);
        }

        //foundUser.setBookRequests(userDto.getBookRequests());

        /* Before access 'stream()' method we need to make sure bookRequests set is not null  */
        if(userDto.getBookRequests() != null)
        {
            foundUser.setBookRequests(userDto.getBookRequests().stream().map(br->modelMapper.map(br, BookRequest.class)).collect(Collectors.toSet()));
        }
        else
        {
            foundUser.setBookRequests(null);
        }

        User updatedUser = userRepository.save(foundUser);

        //return modelMapper.map(updatedUser, UserDto.class);
        return modelMapper.map(updatedUser, UserDisplayDto.class);
    }

    @Override
    public void deleteUser(Long userId) {

        //User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID : " + userId + " does not exist !"));
        User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

        userRepository.deleteById(userId);
    }
}
