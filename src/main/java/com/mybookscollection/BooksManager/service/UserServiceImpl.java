package com.mybookscollection.BooksManager.service;

import com.mybookscollection.BooksManager.dto.UserDto;
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
    public UserDto createUser(UserDto userDto) {

        User newUser = modelMapper.map(userDto, User.class);

        User savedUser = userRepository.save(newUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> allUsers = userRepository.findAll();

        return allUsers.stream().map((u)->modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {

        //User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID : " + userId + " does not exist !"));
        User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

        return modelMapper.map(foundUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {

        //User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID : " + userId + " does not exist !"));
        User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

        foundUser.setUserName(userDto.getUserName());
        foundUser.setUserEmail(userDto.getUserEmail());
        foundUser.setUserPassword(userDto.getUserPassword());
        foundUser.setUserCountry(userDto.getUserCountry());
        foundUser.setUserJoinedDate(userDto.getUserJoinedDate());
        foundUser.setBooks(userDto.getBooks());
        foundUser.setBookRequests(userDto.getBookRequests());

        User updatedUser = userRepository.save(foundUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {

        //User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID : " + userId + " does not exist !"));
        User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

        userRepository.deleteById(userId);
    }
}
