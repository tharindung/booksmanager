package com.mybookscollection.BooksManager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name="user_name", nullable = false)
    private String userName;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_country", referencedColumnName = "country_id", nullable = false)
    private Country userCountry;

    @Column(name="user_email", nullable = false)
    private String userEmail;

    @Column(name="user_password", nullable = false)
    private String userPassword;

    @Column(name="user_joined_date", nullable = false)
    private LocalDate userJoinedDate;

    @JsonIgnore
    @OneToMany(mappedBy = "bookOwner")
    private Set<Book> books = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "requestedUser")
    private Set<BookRequest> bookRequests = new HashSet<>();
}
