package com.lib_for_mentor.lib_for_mentor.Repository;

import com.lib_for_mentor.lib_for_mentor.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByTitle(String title);
}
