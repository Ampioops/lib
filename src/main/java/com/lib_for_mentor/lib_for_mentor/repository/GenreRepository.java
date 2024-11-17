package com.lib_for_mentor.lib_for_mentor.repository;

import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
