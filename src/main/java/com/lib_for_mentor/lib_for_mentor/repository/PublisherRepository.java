package com.lib_for_mentor.lib_for_mentor.repository;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import com.lib_for_mentor.lib_for_mentor.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>, JpaSpecificationExecutor<Publisher>{
    @EntityGraph(attributePaths = {"books"})
    Page<Publisher> findAll(@Nullable Specification<Publisher> spec, Pageable pageable);

}
