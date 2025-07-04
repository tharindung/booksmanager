package com.mybookscollection.BooksManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_request")
public class BookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookRequestId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_request_book_id", referencedColumnName = "book_id", nullable = false)
    private Book book;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_request_user_id", referencedColumnName = "user_id", nullable = false)
    private User requestedUser;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_request_status_id", referencedColumnName = "request_status_id", nullable = false)
    private RequestStatus requestStatus;
}
