package com.lib_for_mentor.lib_for_mentor.entity;


import com.lib_for_mentor.lib_for_mentor.model.CreateBookRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;
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

    @Column
    private String title;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column
    private Integer pages;

    @Column
    private String description;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", published_year=" + publishedYear +
                ", pages=" + pages +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(publishedYear, book.publishedYear) && Objects.equals(pages, book.pages) && Objects.equals(title, book.title) && Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publishedYear, pages, description);
    }
}
