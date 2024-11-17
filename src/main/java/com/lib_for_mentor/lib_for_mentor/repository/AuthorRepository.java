package com.lib_for_mentor.lib_for_mentor.repository;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
