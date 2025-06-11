package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.*;
import com.mybookscollection.BooksManager.entity.BookRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "requestStatusId")
public class RequestStatusDto {

    private Long requestStatusId;

    @NotEmpty(message = "Request Status should not be empty !")
    private String requestStatus;

    //@JsonBackReference(value="reqStatus")
    @JsonIgnore//To reduce the complexity of the Json payload we can ignore this
    private Set<BookRequestDto> bookRequests = new HashSet<>();
}
