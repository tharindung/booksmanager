package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import com.mybookscollection.BooksManager.entity.Book;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookConditionId")
public class BookConditionDto {

    private Long bookConditionId;

    @NotEmpty(message = "Book Condition should not be empty !")
    private String bookCondition;

    //@JsonManagedReference(value="bookCondition")
    @JsonIgnore//To reduce the complexity of the Json payload we can ignore this
    private Set<BookDto> books = new HashSet<>();
 }
