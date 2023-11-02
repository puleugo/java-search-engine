package com.megabrain.javasearchengine.controller;

import com.megabrain.javasearchengine.dto.BookProfileResponse;
import com.megabrain.javasearchengine.dto.CreateBookRequest;
import com.megabrain.javasearchengine.model.Book;
import com.megabrain.javasearchengine.service.BookService;
import com.megabrain.javasearchengine.util.BasePaginationEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequestMapping("/books")
@RestController
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity getBooks() {
        List<Book> books = bookService.findAllBooks();
        return new ResponseEntity(books, HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public BasePaginationEntity<BookProfileResponse> getPaginationBooks(
        @RequestParam("offset") Integer offset,@RequestParam("fetch") Integer fetch, @RequestParam("sortBy") String sortBy) {
        BasePaginationEntity<BookProfileResponse> paginationBooks = bookService.findByPage(offset, fetch, sortBy);
        return paginationBooks;
    }

    @GetMapping("/{id}")
    public ResponseEntity getBookById(@PathVariable("id") Long id) {
        Book book = bookService.findBookById(id);
        BookProfileResponse bookProfileResponse = BookProfileResponse.from(book);
        return new ResponseEntity(bookProfileResponse, HttpStatus.OK);
    }

    @PostMapping()
    @Transactional
    public ResponseEntity createBook(@RequestBody CreateBookRequest createBookRequest) {
        Book book = bookService.createBook(createBookRequest);
        BookProfileResponse bookProfileResponse = BookProfileResponse.from(book);
        return new ResponseEntity(bookProfileResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<BookProfileResponse> updateBook(@PathVariable("id") Long id, @RequestBody CreateBookRequest createBookRequest) {
        bookService.updateBook(id, createBookRequest);
        Book book = bookService.findBookById(id);
        BookProfileResponse bookProfileResponse = BookProfileResponse.from(book);
        return new ResponseEntity(bookProfileResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookProfileResponse> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
