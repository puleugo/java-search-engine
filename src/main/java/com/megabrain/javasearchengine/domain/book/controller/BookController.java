package com.megabrain.javasearchengine.domain.book.controller;

import com.megabrain.javasearchengine.domain.book.model.BookAndIsRented;
import com.megabrain.javasearchengine.domain.book.model.BookRent;
import com.megabrain.javasearchengine.domain.book.service.BookService;
import com.megabrain.javasearchengine.domain.book.dto.BookProfileResponseDTO;
import com.megabrain.javasearchengine.domain.book.dto.BookCreateRequestDTO;
import com.megabrain.javasearchengine.domain.book.model.Book;
import com.megabrain.javasearchengine.domain.user.model.User;
import com.megabrain.javasearchengine.domain.user.model.UserRole;
import com.megabrain.javasearchengine.global.annotation.LoginGuards;
import com.megabrain.javasearchengine.global.annotation.LoginUser;
import com.megabrain.javasearchengine.global.annotation.RequiredRole;
import com.megabrain.javasearchengine.global.util.BasePaginationEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/books")
@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<List<BookProfileResponseDTO>> getBooks() {
        List<BookAndIsRented> bookAndIsRenteds = this.bookService.findAllBooks();
        List<BookProfileResponseDTO> books = bookAndIsRenteds.stream().map(BookProfileResponseDTO::from).collect(Collectors.toList());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/deadlines")
    @RequiredRole({UserRole.ADMIN})
    public ResponseEntity<List<BookRent>> getRentBooks() {
        List<BookRent> books = this.bookService.getRentBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<BasePaginationEntity<BookProfileResponseDTO>> getPaginationBooks(
        @RequestParam("offset") Integer offset,@RequestParam("fetch") Integer fetch, @RequestParam("sortBy") String sortBy) {
        BasePaginationEntity<BookProfileResponseDTO> paginationBooks = this.bookService.findByPage(offset, fetch, sortBy);
        return new ResponseEntity<>(paginationBooks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookProfileResponseDTO> getBookById(@PathVariable("id") Long id) {
        BookAndIsRented book = this.bookService.findBookAndIsRentById(id);
        BookProfileResponseDTO bookProfileResponseDTO = BookProfileResponseDTO.from(book);
        return new ResponseEntity<>(bookProfileResponseDTO, HttpStatus.OK);
    }

    @PostMapping()
    @Transactional
    @LoginGuards
    @RequiredRole({UserRole.ADMIN})
    public ResponseEntity<BookProfileResponseDTO> createBook(@RequestBody BookCreateRequestDTO bookCreateRequestDTO) {
        Book book = this.bookService.createBook(bookCreateRequestDTO);
        BookProfileResponseDTO bookProfileResponseDTO = BookProfileResponseDTO.from(book);
        return new ResponseEntity<>(bookProfileResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/rent")
    @RequiredRole({UserRole.USER, UserRole.ADMIN})
    public ResponseEntity rentBook(@PathVariable("id") Long id, @LoginUser User user) {
        this.bookService.rentBookById(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Transactional
    @RequiredRole({UserRole.ADMIN})
    public ResponseEntity<BookProfileResponseDTO> updateBook(@PathVariable("id") Long id, @RequestBody BookCreateRequestDTO bookCreateRequestDTO) {
        this.bookService.updateBook(id, bookCreateRequestDTO);
        BookAndIsRented book = this.bookService.findBookAndIsRentById(id);
        BookProfileResponseDTO bookProfileResponseDTO = BookProfileResponseDTO.from(book);
        return new ResponseEntity<>(bookProfileResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @RequiredRole({UserRole.ADMIN})
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        this.bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
