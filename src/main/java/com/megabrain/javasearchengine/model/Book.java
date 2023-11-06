package com.megabrain.javasearchengine.model;

import com.megabrain.javasearchengine.dto.CreateBookRequest;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(generator = "increment")
    private Long id;
    private String name;
    private String author;
    private String publisher;
    @Column(name = "published_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime publishedAt;
    private Boolean isRented;

    public static Book from(CreateBookRequest createBookRequest) {
        return Book.builder()
                .name(createBookRequest.getName())
                .author(createBookRequest.getAuthor())
                .publisher(createBookRequest.getPublisher())
                .isRented(createBookRequest.getIsRented())
                .publishedAt(LocalDateTime.now())
                .build();
    }
}