package com.megabrain.javasearchengine.domain.book.model;

import com.megabrain.javasearchengine.domain.book.dto.BookCreateRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(name = "published_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime publishedAt;

    public static Book from(BookCreateRequestDTO bookCreateRequestDTO) {
        return Book.builder()
                .name(bookCreateRequestDTO.getName())
                .author(bookCreateRequestDTO.getAuthor())
                .publisher(bookCreateRequestDTO.getPublisher())
                .publishedAt(LocalDateTime.now())
                .build();
    }
}