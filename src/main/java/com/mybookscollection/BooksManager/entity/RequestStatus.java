package com.mybookscollection.BooksManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Entity
@Table(name = "request_status")
public class RequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_status_id")
    private Long requestStatusId;

    @Column(name = "request_status", nullable = false)
    private String requestStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "requestStatus")
    private Set<BookRequest> bookRequests = new HashSet<>();
}
