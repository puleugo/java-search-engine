package com.megabrain.javasearchengine.domain.book.service;

import com.megabrain.javasearchengine.domain.book.dao.BookRentDao;
import com.megabrain.javasearchengine.domain.book.model.BookAndIsRented;
import com.megabrain.javasearchengine.domain.book.dto.BookProfileResponseDTO;
import com.megabrain.javasearchengine.domain.book.dto.BookCreateRequestDTO;
import com.megabrain.javasearchengine.domain.book.dao.BookDao;
import com.megabrain.javasearchengine.domain.book.model.Book;
import com.megabrain.javasearchengine.domain.book.model.BookRent;
import com.megabrain.javasearchengine.domain.user.model.User;
import com.megabrain.javasearchengine.global.constants.ServicePolicy;
import com.megabrain.javasearchengine.global.util.BasePaginationEntity;
import com.megabrain.javasearchengine.global.util.PaginationMetadataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookDao bookDao;
    private BookRentDao bookRentDao;

    BookService(BookDao bookDao, BookRentDao bookRentDao) {
        this.bookDao = bookDao;
        this.bookRentDao = bookRentDao;
    }

    public BasePaginationEntity<BookProfileResponseDTO> findByPage(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<BookAndIsRented> books = bookDao.findAllBookAndIsRentedPage(pageable);
        Long totalSize = bookDao.count();
        Long currentPage = (long) pageable.getPageNumber();
        Long fetch =(long) pageable.getPageSize();
        Long totalPage = totalSize / pageable.getPageSize();
        BookAndIsRented[] bookArray = (BookAndIsRented[]) books.getContent().toArray();
        List<BookProfileResponseDTO> dtos = Arrays.stream(bookArray).map(BookProfileResponseDTO::from).collect(Collectors.toList());
        PaginationMetadataEntity paginationMetadataEntity = PaginationMetadataEntity.of(currentPage, books.stream().count(), fetch, totalPage, totalSize);
        return new BasePaginationEntity<>(dtos, paginationMetadataEntity);
    }

    public Book createBook(BookCreateRequestDTO bookCreateRequestDTO) {
        Book book = Book.from(bookCreateRequestDTO);
        bookDao.save(book);
        return book;
    }

    public void updateBook(Long id, BookCreateRequestDTO bookCreateRequestDTO) {
        Optional<Book> optionalBook = bookDao.findById(id);
        if (!optionalBook.isPresent()) throw new RuntimeException("Book not found");
        Book book = Book.from(bookCreateRequestDTO);
        book.setId(id);
        bookDao.save(book);
    }

    public void deleteBook(Long id) {
        Boolean exist = bookDao.existsById(id);
        if (!exist) throw new RuntimeException("Book not found");
        bookDao.deleteById(id);
    }

    public BookAndIsRented findBookAndIsRentById(Long id)  {
        BookAndIsRented book = bookDao.findBookAndIsRentById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return book;
    }

    public List<BookAndIsRented> findAllBooks() {
        List<BookAndIsRented> books = bookDao.findAllBookAndIsRenteds();
        return books;
    }

    public List<BookRent> getRentBooks() {
        List<BookRent> books = bookRentDao.findByBookRentDeadlineBefore(LocalDateTime.now());
        return books;
    }

    public void rentBookById(User user, Long bookId) {
        Book book = this.bookDao.findById(bookId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        LocalDate deadline = LocalDate.now().plusDays(ServicePolicy.RENT_PERIOD_DAYS);
        BookRent bookRent = BookRent.of(book, user, deadline);
        this.bookRentDao.save(bookRent);
    }
}
