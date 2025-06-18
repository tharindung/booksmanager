package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.Country;
import com.mybookscollection.BooksManager.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    private User user;
    private Country country;
    private Country savedCountry;

    @BeforeEach
    public void setup()
    {
        country = Country.builder().country("Japan").build();

        savedCountry = countryRepository.save(country);

        user = User.builder().
                userName("John Doe").
                userEmail("john.doe@gmail.com").
                userPassword("12345").
                userJoinedDate(LocalDate.parse("2025-06-15")).
                userCountry(savedCountry).build();

    }

    //Junit test for create user operation
    @DisplayName("Junit test for create user operation")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUserObject()
    {
        //Given - precondition or setup

        //When - action or the behavior we're testing
        User savedUser = userRepository.save(user);

        //Then - verify the output
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUserId()).isGreaterThan(0);
    }

    //Junit test for get all users operation
    @DisplayName("Junit test for get all users operation")
    @Test
    public void givenUsersList_whenFindAll_thenReturnUsersList()
    {
        //Given - precondition or setup
        User user1 = User.builder().
                userName("Jane Doe").
                userEmail("jane.doe@gmail.com").
                userPassword("67890").
                userJoinedDate(LocalDate.parse("2025-06-01")).
                userCountry(savedCountry).build();

        userRepository.save(user);
        userRepository.save(user1);

        //When - action or the behavior we're testing
        List<User> allUsers = userRepository.findAll();

        //Then - verify the output
        Assertions.assertThat(allUsers).isNotNull();
        Assertions.assertThat(allUsers.size()).isEqualTo(2);

    }

    //Junit test for get user by id operation
    @DisplayName("Junit test for get user by id operation")
    @Test
    public void givenUserObject_whenFindById_thenReturnUserObject()
    {
        //Given - precondition or setup
        userRepository.save(user);

        //When - action or the behavior we're testing
        User returnedUser = userRepository.findById(user.getUserId()).get();

        //Then - verify the output
        Assertions.assertThat(returnedUser).isNotNull();
    }

    //Junit test for update user operation
    @DisplayName("Junit test for update user operation")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser()
    {
        //Given - precondition or setup
        userRepository.save(user);

        //When - action or the behavior we're testing
        User savedUser = userRepository.findById(user.getUserId()).get();

        savedUser.setUserName("Mark Smith");
        savedUser.setUserEmail("mark.smith@gmail.com");

        User updatedUser = userRepository.save(savedUser);

        //Then - verify the output
        Assertions.assertThat(updatedUser.getUserName()).isEqualTo("Mark Smith");
        Assertions.assertThat(updatedUser.getUserEmail()).isEqualTo("mark.smith@gmail.com");
    }

    //Junit test for delete user operation
    @DisplayName("Junit test for delete user operation")
    @Test
    public void givenUserObject_whenDeleteById_thenRemoveUserObject()
    {
        //Given - precondition or setup
        userRepository.save(user);

        //When - action or the behavior we're testing
        userRepository.deleteById(user.getUserId());

        Optional<User> optionalUser = userRepository.findById(user.getUserId());

        //Then - verify the output
        Assertions.assertThat(optionalUser).isEmpty();
    }
}
