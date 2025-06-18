package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.BookCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookConditionRepositoryTests {

    @Autowired
    private BookConditionRepository bookConditionRepository;

    private BookCondition bookCondition;

    @BeforeEach
    public void setup(){
        bookCondition = BookCondition.builder().bookCondition("Usable").build();
    }

    //Junit test for create book condition operation
    @DisplayName("Junit test for create book condition operation")
    @Test
    public void givenBookConditionObject_whenSave_thenReturnSavedBookConditionObject()
    {
        //Given - precondition or setup


        //When - action or the behavior we're testing
        BookCondition savedBookCondition = bookConditionRepository.save(bookCondition);

        //Then - verify the output
        Assertions.assertThat(savedBookCondition).isNotNull();
        Assertions.assertThat(savedBookCondition.getBookConditionId()).isGreaterThan(0);
    }

    //Junit test for get all book conditions
    @DisplayName("Junit test for get all book conditions")
    @Test
    public void givenBookConditionsList_whenFindAll_thenReturnBookConditionsList()
    {
        //Given - precondition or setup
        bookConditionRepository.save(bookCondition);

        BookCondition bookCondition1 = BookCondition.builder().bookCondition("Unusable").build();

        bookConditionRepository.save(bookCondition1);

        //When - action or the behavior we're testing
        List<BookCondition> bookConditionsList = bookConditionRepository.findAll();

        //Then - verify the output
        Assertions.assertThat(bookConditionsList).isNotNull();
        Assertions.assertThat(bookConditionsList.size()).isEqualTo(2);
    }

    //Junit test for get book condition by id
    @DisplayName("Junit test for get book condition by id")
    @Test
    public void givenBookConditionObject_whenFindById_thenReturnBookConditionObject()
    {
        //Given - precondition or setup
        bookConditionRepository.save(bookCondition);

        //When - action or the behavior we're testing
        BookCondition returnedBookCondition = bookConditionRepository.findById(bookCondition.getBookConditionId()).get();

        //Then - verify the output
        Assertions.assertThat(returnedBookCondition).isNotNull();
    }

    //Junit test for update book condition
    @DisplayName("Junit test for update book condition")
    @Test
    public void givenBookConditionObject_whenUpdateBookCondition_thenReturnUpdatedBookConditionObject(){

        //Given - precondition or setup
        bookConditionRepository.save(bookCondition);

        //When - action or the behavior we're testing
        BookCondition savedBookCondition = bookConditionRepository.findById(bookCondition.getBookConditionId()).get();

        savedBookCondition.setBookCondition("New");

        BookCondition updatedBookCondition = bookConditionRepository.save(savedBookCondition);

        //Then - verify the output
        Assertions.assertThat(updatedBookCondition.getBookCondition()).isEqualTo("New");
    }

    //Junit test for delete book condition
    @DisplayName("Junit test for delete book condition")
    @Test
    public void givenBookCondition_whenDeleteById_thenRemoveBookCondition()
    {
        //Given - precondition or setup
        bookConditionRepository.save(bookCondition);

        //When - action or the behavior we're testing
        bookConditionRepository.deleteById(bookCondition.getBookConditionId());

        Optional<BookCondition> optionalBookCondition = bookConditionRepository.findById(bookCondition.getBookConditionId());

        //Then - verify the output
        Assertions.assertThat(optionalBookCondition).isEmpty();
    }
}
