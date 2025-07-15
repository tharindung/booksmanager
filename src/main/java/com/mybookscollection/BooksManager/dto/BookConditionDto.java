package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import com.mybookscollection.BooksManager.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing a Book Condition")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookConditionId")
public class BookConditionDto {

    @Schema(description = "Unique identifier of the book condition", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long bookConditionId;

    @NotEmpty(message = "Book Condition should not be empty !")
    @Schema(description = "Name of the Book Condition", example = "Used", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bookCondition;

    //@JsonManagedReference(value="bookCondition")
    @JsonIgnore//To reduce the complexity of the Json payload we can ignore this
    @Schema(description = "Set of books under this condition (ignored in JSON)", hidden = true)
    private Set<BookDto> books = new HashSet<>();
 }
