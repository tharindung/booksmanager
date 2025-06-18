package com.mybookscollection.BooksManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder //This annotation is needed for unit testing
@Entity
@Table(name = "book_category")
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_category_id")
    private Long bookCategoryId;

    @Column(name="book_category", nullable = false)
    private String bookCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "bookCategory")
    private Set<Book> books = new HashSet<>();
}
