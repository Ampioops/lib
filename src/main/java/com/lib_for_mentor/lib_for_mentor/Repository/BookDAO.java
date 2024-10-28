package com.lib_for_mentor.lib_for_mentor.Repository;

import com.lib_for_mentor.lib_for_mentor.Entity.Book;

import java.util.List;

public interface BookDAO {
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(Book book);
    List<Book> getAllBooks();
    Book findBookByTitle(String title);
}
