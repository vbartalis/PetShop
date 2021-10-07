package io.github.vbartalis.petshop.repository.criteriaRepository;

import io.github.vbartalis.petshop.dto.post.PostPageCriteria;
import io.github.vbartalis.petshop.dto.post.PostSearchCriteria;
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

/**
 * This interface is a Repository for the {@code Post} Entity.
 */
@Repository
public class PostCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public PostCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    /**
     * This method finds all Posts that match the provided criteria.
     *
     * @param postPageCriteria   The properties of the page returned.
     * @param postSearchCriteria The criteria by which the returned page of entities should be filtered.
     * @return Returns a page of {@code Post} entities.
     */
    public Page<Post> findAllWithFilters(PostPageCriteria postPageCriteria, PostSearchCriteria postSearchCriteria) {
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);
        Predicate predicate = getPredicate(postSearchCriteria, postRoot);
        criteriaQuery.where(predicate);
        setOrder(postPageCriteria, criteriaQuery, postRoot);

        TypedQuery<Post> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(postPageCriteria.getPageNumber() * postPageCriteria.getPageSize());
        typedQuery.setMaxResults(postPageCriteria.getPageSize());

        Pageable pageable = getPageable(postPageCriteria);

        long postsCount = getPostsCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, postsCount);
    }

    /**
     * This method is used to create a {@code Predicate} object, that contains a filtering query.
     *
     * @param postSearchCriteria The {@code PostSearchCriteria} object that contains the filtering properties.
     * @param postRoot           The {@code Root<Post>} object.
     * @return Returns a {@code Predicate} object, that contains a filtering query.
     */
    private Predicate getPredicate(PostSearchCriteria postSearchCriteria, Root<Post> postRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(postSearchCriteria.getId())) {
            predicates.add(criteriaBuilder.equal(postRoot.get("id"), postSearchCriteria.getId()));
        }
        if (Objects.nonNull(postSearchCriteria.getTitle())) {
            predicates.add(criteriaBuilder.like(postRoot.get("title"), "%" + postSearchCriteria.getTitle() + "%"));
        }
        if (Objects.nonNull(postSearchCriteria.getDescription())) {
            predicates.add(criteriaBuilder.like(postRoot.get("description"), "%" + postSearchCriteria.getDescription() + "%"));
        }
        if (Objects.nonNull(postSearchCriteria.getIsPublic())) {
            predicates.add(criteriaBuilder.equal(postRoot.get("isPublic"), postSearchCriteria.getIsPublic()));
        }
        if (Objects.nonNull(postSearchCriteria.getUserId())) {
            predicates.add(criteriaBuilder.equal(postRoot.get("user").get("id"), postSearchCriteria.getUserId()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * This method sets how should the criteriaQuery be ordered by.
     *
     * @param postPageCriteria The {@code PostPage} object, contains what should the criteriaQuery be ordered by.
     * @param criteriaQuery    The {@code CriteriaQuery<Post>} object.
     * @param postRoot         The {@code Root<Post>} object.
     */
    private void setOrder(PostPageCriteria postPageCriteria,
                          CriteriaQuery<Post> criteriaQuery,
                          Root<Post> postRoot) {
        if (postPageCriteria.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(postRoot.get(postPageCriteria.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(postRoot.get(postPageCriteria.getSortBy())));
        }
    }

    /**
     * This method creates a {@code Pageable} object from the provided {@code PostPage} object.
     *
     * @param postPageCriteria The {@code PostPage} object.
     * @return Returns a {@code Pageable} object.
     */
    private Pageable getPageable(PostPageCriteria postPageCriteria) {
        Sort sort = Sort.by(postPageCriteria.getSortDirection(), postPageCriteria.getSortBy());
        return PageRequest.of(postPageCriteria.getPageNumber(), postPageCriteria.getPageSize(), sort);
    }

    /**
     * This method returns the number of {@code Post} objects filtered by the predicate.
     *
     * @param predicate The {@code Predicate} object, that contains a filtering query.
     * @return Returns the number of {@code Post} objects filtered by the predicate.
     */
    private long getPostsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Post> countRoot = countQuery.from(Post.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
