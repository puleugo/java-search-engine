package com.megabrain.javasearchengine.domain.book.dao;

import com.megabrain.javasearchengine.domain.book.model.BookAndIsRented;
import com.megabrain.javasearchengine.domain.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDao extends PagingAndSortingRepository<Book, Long> {
    Optional<Book> findById(Long id);

    @Query("SELECT b AS book," +
        " CASE WHEN rent.deadline IS NULL THEN 'false' ELSE 'true' END AS isRented" +
        " FROM Book b" +
        " LEFT JOIN BookRent rent ON b.id = rent.book.id" +
        " WHERE b.id = :id")
    Optional<BookAndIsRented> findBookAndIsRentById(Long id);

    @Query("SELECT b AS book," +
        " CASE WHEN rent.deadline IS NULL THEN 'false' ELSE 'true' END AS isRented" +
        " FROM Book b" +
        " LEFT JOIN BookRent rent ON b.id = rent.book.id")
    Page<BookAndIsRented> findAllBookAndIsRentedPage(Pageable pageable);

    @Query("SELECT b AS book," +
        " CASE WHEN rent.deadline IS NULL THEN 'false' ELSE 'true' END AS isRented" +
        " FROM Book b" +
        " LEFT JOIN BookRent rent ON b.id = rent.book.id")
    List<BookAndIsRented> findAllBookAndIsRenteds();

    @Query("SELECT b AS book FROM Book b WHERE b.name LIKE %:keyword%")
    List<BookAndIsRented> findBookAndIsRentByKeyword(String keyword);


    @Query("SELECT b FROM Book b ORDER BY b.publishedAt DESC")
    Page<Book> findBooksSortedByPublishedAt(Pageable pageable);
}