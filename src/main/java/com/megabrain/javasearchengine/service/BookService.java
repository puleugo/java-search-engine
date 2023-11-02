package com.megabrain.javasearchengine.service;

import com.megabrain.javasearchengine.dto.BookProfileResponse;
import com.megabrain.javasearchengine.dto.CreateBookRequest;
import com.megabrain.javasearchengine.dao.BookDao;
import com.megabrain.javasearchengine.model.Book;
import com.megabrain.javasearchengine.util.BasePaginationEntity;
import com.megabrain.javasearchengine.util.PaginationMetadataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public BasePaginationEntity<BookProfileResponse> findByPage(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Book> books = bookDao.findAll(pageable);
        Long totalSize = bookDao.count();
        Long currentPage = (long) pageable.getPageNumber();
        Long fetch =(long) pageable.getPageSize();
        Long totalPage = totalSize / pageable.getPageSize();
        Book[] bookArray = (Book[]) books.getContent().toArray();
        List<BookProfileResponse> dtos = Arrays.stream(bookArray).map(BookProfileResponse::from).collect(Collectors.toList());
        PaginationMetadataEntity paginationMetadataEntity = PaginationMetadataEntity.of(currentPage, books.stream().count(), fetch, totalPage, totalSize);
        return new BasePaginationEntity<>(dtos, paginationMetadataEntity);
    }

    public Book createBook(CreateBookRequest createBookRequest) {
        Book book = Book.from(createBookRequest);
        bookDao.save(book);
        return book;
    }

    public void updateBook(Long id, CreateBookRequest createBookRequest) {
        Optional<Book> optionalBook = bookDao.findById(id);
        if (!optionalBook.isPresent()) throw new RuntimeException("Book not found");
        Book book = Book.from(createBookRequest);
        book.setId(id);
        bookDao.save(book);
        book.setId(id);
    }

    public void deleteBook(Long id) {
        Boolean exist = bookDao.existsById(id);
        if (!exist) throw new RuntimeException("Book not found");
        bookDao.deleteById(id);
    }

    public Book findBookById(Long id)  {
        Book book = bookDao.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return book;
    }

    public List<Book> findAllBooks() {
        List<Book> books = (List<Book>) bookDao.findAll();
        return books;
    }
}
