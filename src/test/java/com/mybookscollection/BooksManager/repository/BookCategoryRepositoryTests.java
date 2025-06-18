package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.BookCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookCategoryRepositoryTests {

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    private BookCategory bookCategory;

    @BeforeEach
    public void setup(){

        bookCategory = BookCategory.builder().bookCategory("Programming").build();
    }

    //Junit test for create book category operation
    @DisplayName("Junit test for create book category operation")
    @Test
    public void givenBookCategoryObject_whenSave_thenReturnSavedBookCategoryObject()
    {
        //Given - precondition or setup

        //When - action or the behavior we're testing
        BookCategory savedBookCategory = bookCategoryRepository.save(bookCategory);

        //Then - verify the output
        Assertions.assertThat(savedBookCategory).isNotNull();
        Assertions.assertThat(savedBookCategory.getBookCategoryId()).isGreaterThan(0);
    }

    //Junit test for get all book categories operation
    @DisplayName("Junit test for get all book categories operation")
    @Test
    public void givenBookCategoriesList_whenFindAll_thenReturnBookCategoriesList(){

        //Given - precondition or setup
        bookCategoryRepository.save(bookCategory);

        BookCategory bookCategory1 = BookCategory.builder().bookCategory("Science").build();

        bookCategoryRepository.save(bookCategory1);

        //When - action or the behavior we're testing
        List<BookCategory> allBookCategories = bookCategoryRepository.findAll();

        //Then - verify the output
        Assertions.assertThat(allBookCategories).isNotNull();
        Assertions.assertThat(allBookCategories.size()).isEqualTo(2);
    }

    //Junit test for get book category by id operation
    @DisplayName("Junit test for get book category by id operation")
    @Test
    public void givenBookCategoryObject_whenFindById_thenReturnBookCategoryObject()
    {
        //Given - precondition or setup
        bookCategoryRepository.save(bookCategory);

        //When - action or the behavior we're testing
        BookCategory returnedBookCategory = bookCategoryRepository.findById(bookCategory.getBookCategoryId()).get();

        //Then - verify the output
        Assertions.assertThat(returnedBookCategory).isNotNull();
    }

    //Junit test for update book category operation
    @DisplayName("Junit test for update book category operation")
    @Test
    public void givenBookCategoryObject_whenUpdateBookCategory_thenReturnUpdatedBookCategoryObject()
    {
        //Given - precondition or setup
        bookCategoryRepository.save(bookCategory);

        //When - action or the behavior we're testing
        BookCategory savedBookCategory = bookCategoryRepository.findById(bookCategory.getBookCategoryId()).get();

        savedBookCategory.setBookCategory("Cooking");

        BookCategory updatedBookCategory = bookCategoryRepository.save(savedBookCategory);

        //Then - verify the output
        Assertions.assertThat(updatedBookCategory.getBookCategory()).isEqualTo("Cooking");
    }

    //Junit test for delete book category operation
    @DisplayName("Junit test for delete book category operation")
    @Test
    public void givenBookCategoryObject_whenDeleteById_thenRemoveBookCategoryObject()
    {
        //Given - precondition or setup
        bookCategoryRepository.save(bookCategory);

        //When - action or the behavior we're testing
        bookCategoryRepository.deleteById(bookCategory.getBookCategoryId());

        Optional<BookCategory> optionalBookCategory = bookCategoryRepository.findById(bookCategory.getBookCategoryId());

        //Then - verify the output
        Assertions.assertThat(optionalBookCategory).isEmpty();
    }
}
