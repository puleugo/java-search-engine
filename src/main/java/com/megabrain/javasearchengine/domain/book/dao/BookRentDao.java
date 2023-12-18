package com.megabrain.javasearchengine.domain.book.dao;

import com.megabrain.javasearchengine.domain.book.model.BookRent;
import com.megabrain.javasearchengine.domain.book.model.BookRentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookRentDao extends JpaRepository<BookRent, BookRentId> {

    @Query("SELECT rent FROM BookRent rent " +
        "INNER JOIN Book book ON rent.book.id = book.id " +
        "INNER JOIN User user ON rent.user.id = user.id " +
        "WHERE rent.deadline < :deadline")
    List<BookRent> findByBookRentDeadlineBefore(LocalDate deadline);

    @Query("SELECT rent FROM BookRent rent " +
            "JOIN FETCH rent.book " +
            "WHERE rent.user.id = :id")
    List<BookRent> findByUserId(Long id);


    @Query("SELECT rent FROM BookRent rent " +
            "JOIN FETCH rent.book " +
            "WHERE rent.book.id = :id")
    BookRent findByBookId(Long id);
}
