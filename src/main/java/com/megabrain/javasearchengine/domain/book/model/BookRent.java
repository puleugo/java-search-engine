package com.megabrain.javasearchengine.domain.book.model;

import com.megabrain.javasearchengine.domain.user.model.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book_rents")
@IdClass(BookRentId.class)
public class BookRent {
    @Id
    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id")
    private Book book;

    @Id
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    private BookRent(Book book, User user, LocalDate deadline) {
        this.book = book;
        this.user = user;
        this.deadline = deadline;
    }

    public BookRent() {}

    public static BookRent of(Book book, User user, LocalDate deadline) {
        return new BookRent(book, user, deadline);
    }
}
