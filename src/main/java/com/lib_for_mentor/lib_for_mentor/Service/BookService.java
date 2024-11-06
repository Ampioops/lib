package com.lib_for_mentor.lib_for_mentor.Service;

import com.lib_for_mentor.lib_for_mentor.entities.Book;
import com.lib_for_mentor.lib_for_mentor.models.BookResponce;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    void updateBookInfo(int id, String description, int pages, int published_year, String title);
    Book deleteById(int id);
    List<BookResponce> getAllBooks();
    List<Book> findAllByTitle(String title);
}
