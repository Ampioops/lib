package com.lib_for_mentor.lib_for_mentor.repository;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    Page<Book> findByUsersId(Integer userId, Pageable pageable);
}
