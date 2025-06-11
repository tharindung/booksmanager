package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import com.mybookscollection.BooksManager.entity.Book;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookCategoryId")
public class BookCategoryDto {

    private Long bookCategoryId;

    @NotEmpty(message = "Book Category should not be empty !")
    private String bookCategory;

    //@JsonManagedReference(value="bookCategory")
    @JsonIgnore //To reduce the complexity of the Json payload we can ignore this
    private Set<BookDto> books = new HashSet<>();
}
