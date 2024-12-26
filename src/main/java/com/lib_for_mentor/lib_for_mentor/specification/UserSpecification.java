package com.lib_for_mentor.lib_for_mentor.specification;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.User;
import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.param.UserParamsDTO;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecification {
    public Specification<User> build(UserParamsDTO params) {
        if (params == null) {
            return null;
        }
        return withFirstNameCont(params.getFirstNameCont())
                .and(withLastNameCont(params.getLastNameCont()))
                .and(hasBookId(params.getBookIdCont()))
                .and(withEmailCont(params.getEmailCont()));
    }

    private Specification<User> withFirstNameCont(String firstName) {
        return ((root, query, cb) ->
                firstName != null ? cb.equal(root.get("firstName"), firstName) : cb.conjunction());
    }

    private Specification<User> withLastNameCont(String lastName) {
        return ((root, query, cb) ->
                lastName != null ? cb.equal(root.get("firstName"), lastName) : cb.conjunction());
    }

    private Specification<User> withEmailCont(String email) {
        return ((root, query, cb) ->
                email != null ? cb.equal(root.get("email"), email) : cb.conjunction());
    }

    private Specification<User> hasBookId(Integer bookId) {
        return (root, query, criteriaBuilder) -> {
            // Получаем связь ManyToMany между User и Book
            return criteriaBuilder.isMember(bookId, root.get("books").get("id"));
        };
    }
}

