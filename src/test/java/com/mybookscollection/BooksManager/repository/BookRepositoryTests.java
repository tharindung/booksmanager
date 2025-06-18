package com.mybookscollection.BooksManager.repository;

import com.mybookscollection.BooksManager.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private BookConditionRepository bookConditionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    private Book book;

    private BookCategory bookCategory;

    private BookCategory savedBookCategory;

    private BookCondition bookCondition;

    private BookCondition savedBookCondition;

    private User bookOwner;

    private User savedBookOwner;

    private Country country;

    private Country savedCountry;

    @BeforeEach
    public void setup()
    {
        bookCategory = BookCategory.builder().bookCategory("Children").build();

        savedBookCategory = bookCategoryRepository.save(bookCategory);

        bookCondition = BookCondition.builder().bookCondition("Good").build();

        savedBookCondition = bookConditionRepository.save(bookCondition);

        country = Country.builder().country("China").build();

        savedCountry = countryRepository.save(country);

        bookOwner = User.builder().
                        userName("John Doe").
                        userEmail("john.doe@gmail.com").
                        userPassword("12345").
                        userCountry(savedCountry).
                        userJoinedDate(LocalDate.parse("2025-06-01")).build();

        savedBookOwner = userRepository.save(bookOwner);

        book = Book.builder().
                bookName("Harry Potter").
                bookAuthor("J.K.Rowling").
                bookCatalogNo("HP1234").
                bookCategory(savedBookCategory).
                bookCondition(savedBookCondition).
                isBookShareable(true).
                bookOwner(savedBookOwner).build();
    }

    //Junit test for create book operation
    @DisplayName("Junit test for create book operation")
    @Test
    public void givenBookObject_whenSave_thenReturnSavedBookObject()
    {
        //Given - precondition or setup

        //When - action or the behavior we're testing
        Book savedBook = bookRepository.save(book);

        //Then - verify the output
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getBookId()).isGreaterThan(0);
    }

    //Junit test for get all books operation
    @DisplayName("Junit test for get all books operation")
    @Test
    public void givenBookList_whenFindAll_thenReturnBookList()
    {
        //Given - precondition or setup
        bookRepository.save(book);

        Book book1 = Book.builder().
                        bookName("Famous Five").
                        bookAuthor("Enid Blyton").
                        bookCatalogNo("FF1234").
                        bookCategory(savedBookCategory).
                        bookCondition(savedBookCondition).
                        isBookShareable(false).
                        bookOwner(savedBookOwner).build();

        bookRepository.save(book1);

        //When - action or the behavior we're testing
        List<Book> allBooks = bookRepository.findAll();

        //Then - verify the output
        Assertions.assertThat(allBooks).isNotNull();
        Assertions.assertThat(allBooks.size()).isEqualTo(2);
    }

    //Junit test for get book by id operation
    @DisplayName("Junit test for get book by id operation")
    @Test
    public void givenBookObject_whenFindById_thenReturnBookObject()
    {
        //Given - precondition or setup
        bookRepository.save(book);

        //When - action or the behavior we're testing
        Book returnedBook = bookRepository.findById(book.getBookId()).get();

        //Then - verify the output
        Assertions.assertThat(returnedBook).isNotNull();
    }

    //Junit test for update book operation
    @DisplayName("Junit test for update book operation")
    @Test
    public void givenBookObject_whenUpdateBook_thenReturnUpdatedBookObject()
    {
        //Given - precondition or setup
        bookRepository.save(book);

        //When - action or the behavior we're testing
        Book savedBook = bookRepository.findById(book.getBookId()).get();

        savedBook.setBookName("The Adventures of Tin Tin");
        savedBook.setBookAuthor("Herge");

        Book updatedBook = bookRepository.save(savedBook);

        //Then - verify the output
        Assertions.assertThat(updatedBook.getBookName()).isEqualTo("The Adventures of Tin Tin");
        Assertions.assertThat(updatedBook.getBookAuthor()).isEqualTo("Herge");
    }

    //Junit test for delete book operation
    @DisplayName("Junit test for delete book operation")
    @Test
    public void givenBookObject_whenDeleteById_thenRemoveBookObject()
    {
        //Given - precondition or setup
        bookRepository.save(book);

        //When - action or the behavior we're testing
        bookRepository.deleteById(book.getBookId());

        Optional<Book> optionalBook = bookRepository.findById(book.getBookId());

        //Then - verify the output
        Assertions.assertThat(optionalBook).isEmpty();
    }
}
