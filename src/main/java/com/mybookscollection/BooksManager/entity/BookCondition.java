package com.mybookscollection.BooksManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_condition")
public class BookCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_condition_id")
    private Long bookConditionId;

    @Column(name = "book_condition", nullable = false)
    private String bookCondition;

    @JsonIgnore
    @OneToMany(mappedBy = "bookCondition")
    private Set<Book> books = new HashSet<>();
}
