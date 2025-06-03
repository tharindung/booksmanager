package com.mybookscollection.BooksManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name="book_img")
    private byte[] bookImg;

    @Column(name="book_author", nullable = false)
    private String bookAuthor;

    @Column(name="book_purchase_date")
    private LocalDate bookPurchaseDate;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "book_cat_id", referencedColumnName = "book_category_id", nullable = false)
    private BookCategory bookCategory;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_cond_id", referencedColumnName = "book_condition_id", nullable = false)
    private BookCondition bookCondition;

    @Column(name = "book_catalog_no", nullable = false)
    private String bookCatalogNo;

    @Column(name="book_notes")
    private String bookNotes;

    @Column(name="book_share_flag", nullable = false)
    private boolean isBookShareable;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_owner_id", referencedColumnName = "user_id", nullable = false)
    private User bookOwner;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private Set<BookRequest> bookRequests = new HashSet<>();
}
