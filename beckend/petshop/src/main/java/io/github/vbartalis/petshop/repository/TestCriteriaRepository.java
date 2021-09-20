package io.github.vbartalis.petshop.repository;

import io.github.vbartalis.petshop.dto.test.PostPage;
import io.github.vbartalis.petshop.dto.test.PostSearchCriteria;
import io.github.vbartalis.petshop.entity.Post;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TestCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public TestCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Post> findAllWithFilters(PostPage postPage, PostSearchCriteria postSearchCriteria) {
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);
        Predicate predicate = getPredicate(postSearchCriteria, postRoot);
        criteriaQuery.where(predicate);
        setOrder(postPage, criteriaQuery, postRoot);

        TypedQuery<Post> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(postPage.getPageNumber() * postPage.getPageSize());
        typedQuery.setMaxResults(postPage.getPageSize());

        Pageable pageable = getPageable(postPage);

        long postsCount = getPostsCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, postsCount);
    }

    private Predicate getPredicate(PostSearchCriteria postSearchCriteria, Root<Post> postRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(postSearchCriteria)){
        if (Objects.nonNull(postSearchCriteria.getTitle())) {
            predicates.add(criteriaBuilder.like(postRoot.get("id"), "%" + postSearchCriteria.getId() + "%"));
        }
        if (Objects.nonNull(postSearchCriteria.getTitle())) {
            predicates.add(criteriaBuilder.like(postRoot.get("title"), "%" + postSearchCriteria.getTitle() + "%"));
        }
        if (Objects.nonNull(postSearchCriteria.getTitle())) {
            predicates.add(criteriaBuilder.like(postRoot.get("description"), "%" + postSearchCriteria.getDescription() + "%"));
        }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PostPage postPage,
                          CriteriaQuery<Post> criteriaQuery,
                          Root<Post> postRoot) {
        if(postPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(postRoot.get(postPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(postRoot.get(postPage.getSortBy())));
        }
    }

    private Pageable getPageable(PostPage postPage) {
        Sort sort = Sort.by(postPage.getSortDirection(), postPage.getSortBy());
        return PageRequest.of(postPage.getPageNumber(),postPage.getPageSize(), sort);
    }

    private long getPostsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Post> countRoot = countQuery.from(Post.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
