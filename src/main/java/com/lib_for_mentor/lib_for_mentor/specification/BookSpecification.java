package com.lib_for_mentor.lib_for_mentor.specification;

import com.lib_for_mentor.lib_for_mentor.model.param.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecification {

    public Specification<Book> build(BookParamsDTO params) {
        if (params == null) {
            return null;
        }
        return withAuthor(params.getAuthorCont())
                .and(withPagesGt(params.getPagesGt()))
                .and(withPagesLt(params.getPagesLt()))
                .and(withPublishedEq(params.getPublishedEq()))
                .and(withPublishedGt(params.getPublishedGt()))
                .and(withPublishedLt(params.getPublishedLt()))
                .and(withTitle(params.getTitleCont()));
    }

    private static Specification<Book> withTitle(String title) {
        return ((root, query, criteriaBuilder) ->
                title != null ? criteriaBuilder.equal(root.get("title"), title) : criteriaBuilder.conjunction());
    }

    private static Specification<Book> withAuthor(String author) {
        return ((root, query, criteriaBuilder) ->
                author != null ? criteriaBuilder.equal(root.get("author"), author) : criteriaBuilder.conjunction());
    }

    private static Specification<Book> withPublishedGt(Integer year) {
        return ((root, query, criteriaBuilder) ->
                year != 0 ? criteriaBuilder.greaterThan(root.get("publishedYear"), year) : criteriaBuilder.conjunction());
    }

    private static Specification<Book> withPublishedLt(Integer year) {
        return ((root, query, criteriaBuilder) ->
                year != null ? criteriaBuilder.lessThan(root.get("publishedYear"), year) : criteriaBuilder.conjunction());
    }

    private static Specification<Book> withPublishedEq(Integer year) {
        return ((root, query, criteriaBuilder) ->
                year != null ? criteriaBuilder.equal(root.get("publishedYear"), year) : criteriaBuilder.conjunction());
    }

    private static Specification<Book> withPagesGt(Integer pages) {
        return ((root, query, criteriaBuilder) ->
                pages != null ? criteriaBuilder.greaterThan(root.get("pages"), pages) : criteriaBuilder.conjunction());
    }

    private static Specification<Book> withPagesLt(Integer pages) {
        return ((root, query, criteriaBuilder) ->
                pages != null ? criteriaBuilder.lessThan(root.get("pages"), pages) : criteriaBuilder.conjunction());
    }

}
