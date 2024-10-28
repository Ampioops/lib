package com.lib_for_mentor.lib_for_mentor.Entity;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column(name = "published_year")
    private int publishedYear;

    @Column
    private int pages;

    @Column
    private String description;

    public Book() {
    }

    public Book(String description, int pages, int published_year, String title) {
        this.description = description;
        this.pages = pages;
        this.publishedYear = published_year;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public int getPages() {
        return pages;
    }

    public int getPublished_year() {
        return publishedYear;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublished_year(int published_year) {
        this.publishedYear = published_year;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
        return id == book.id && publishedYear == book.publishedYear && pages == book.pages && Objects.equals(title, book.title) && Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publishedYear, pages, description);
    }
}
