package com.lib_for_mentor.lib_for_mentor.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = true)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = true)
    private Publisher publisher;

}
