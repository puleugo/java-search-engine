package com.megabrain.javasearchengine.domain.book.controller;

import com.megabrain.javasearchengine.domain.book.dto.BookRentDeadResponseDto;
import com.megabrain.javasearchengine.domain.book.dto.BookRentResponseDTO;
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

    @GetMapping("/search")
    public ResponseEntity<List<BookProfileResponseDTO>> getBooksByKeyword(@RequestParam("keyword") String keyword) {
        List<BookAndIsRented> bookAndIsRenteds = this.bookService.findBooksByKeyword(keyword);
        List<BookProfileResponseDTO> books = bookAndIsRenteds.stream().map(BookProfileResponseDTO::from).collect(Collectors.toList());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/newbook/{id}")
    public ResponseEntity<BookProfileResponseDTO> getNewBook(@PathVariable Long id) {
        Book book= this.bookService.findNewBook(id);
        BookProfileResponseDTO bookProfileResponseDTO = BookProfileResponseDTO.from(book);
        return new ResponseEntity<>(bookProfileResponseDTO, HttpStatus.OK);
    }


    @GetMapping("/deadlines")
   //@RequiredRole({UserRole.ADMIN})
    public ResponseEntity<List<BookRentDeadResponseDto>> getRentBooks() {
        System.out.println("getRentBooks");
        List<BookRent> books = this.bookService.getRentBooks();
        System.out.println("getRentBooks" + books);
        System.out.println("getRentBooks" + books.size());
        List<BookRentDeadResponseDto> responseDto = books.stream()
                .map(BookRentDeadResponseDto::from)
                .collect(Collectors.toList());

        for(int i= 0 ; i < responseDto.size(); i++){
            System.out.println("getRentBooks" + responseDto.get(i).getName());
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
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
  //  @LoginGuards
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

    @PostMapping("/{id}/return")
    @RequiredRole({UserRole.USER, UserRole.ADMIN})
    public ResponseEntity returnBook(@PathVariable("id") Long id, @LoginUser User user) {
        this.bookService.returnBookById(user, id);
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

    @GetMapping("/myrents")
    @RequiredRole({UserRole.USER, UserRole.ADMIN})
    public ResponseEntity<List<BookRentResponseDTO>> getMyRentedBooks(@LoginUser User user) {
        List<BookRent> rentedBooks = this.bookService.findBooksRentedByUser(user.getId());
        List<BookRentResponseDTO> rentResponses = rentedBooks.stream().map(BookRentResponseDTO::from).collect(Collectors.toList());
        return new ResponseEntity<>(rentResponses, HttpStatus.OK);
    }
}
