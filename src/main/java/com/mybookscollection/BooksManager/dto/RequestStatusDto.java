package com.mybookscollection.BooksManager.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class RequestStatusDto {

    private Long requestStatusId;

    @NotEmpty(message = "Request Status should not be empty !")
    private String requestStatus;

    //private Set<BookRequest> bookRequests = new HashSet<>();
    @JsonBackReference(value="reqStatus")
    private Set<BookRequestDto> bookRequests = new HashSet<>();
}
