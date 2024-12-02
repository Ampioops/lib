package com.lib_for_mentor.lib_for_mentor.specification;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecification {
    public Specification<Author> build(AuthorParamsDTO params) {
        if (params == null) {
            return null;
        }
        return withFirstNameCont(params.getFirstNameCont())
                .and(withLastNameCont(params.getLastNameCont()))
                .and(withBookId(params.getBookIdCont()));
    }

    private Specification<Author> withFirstNameCont(String firstName) {
        return ((root, query, cb) ->
                firstName != null ? cb.equal(root.get("firstName"), firstName) : cb.conjunction());
    }

    private Specification<Author> withLastNameCont(String lastName) {
        return ((root, query, cb) ->
                lastName != null ? cb.equal(root.get("firstName"), lastName) : cb.conjunction());
    }

    private Specification<Author> withBookId(Integer bookId) {
        return (root, query, criteriaBuilder) -> {
            if (bookId == null) {
                return criteriaBuilder.conjunction(); // Если bookId == null, ничего не фильтруем
            }

            // Джоин (связываем таблицу автора с таблицей книг)
            Join<Author, Book> bookJoin = root.join("books");

            // Условие сравнения ID книги
            return criteriaBuilder.equal(bookJoin.get("id"), bookId);
        };
    }
}
