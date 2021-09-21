package io.github.vbartalis.petshop.repository.criteriaRepository;


import io.github.vbartalis.petshop.dto.request.UserPage;
import io.github.vbartalis.petshop.dto.request.UserSearchCriteria;
import io.github.vbartalis.petshop.entity.Post;
import io.github.vbartalis.petshop.entity.User;
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
public class UserCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public UserCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<User> findAllWithFilters(UserPage userPage, UserSearchCriteria userSearchCriteria) {
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate predicate = getPredicate(userSearchCriteria, userRoot);
        criteriaQuery.where(predicate);
        setOrder(userPage, criteriaQuery, userRoot);

        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(userPage.getPageNumber() * userPage.getPageSize());
        typedQuery.setMaxResults(userPage.getPageSize());

        Pageable pageable = getPageable(userPage);

        long usersCount = getPostsCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, usersCount);
    }

    private Predicate getPredicate(UserSearchCriteria userSearchCriteria, Root<User> userRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(userSearchCriteria.getId())) {
            predicates.add(criteriaBuilder.equal(userRoot.get("id"),  userSearchCriteria.getId()));
        }
        if (Objects.nonNull(userSearchCriteria.getUsername())) {
            predicates.add(criteriaBuilder.like(userRoot.get("username"), "%" + userSearchCriteria.getUsername() + "%"));
        }
        if (Objects.nonNull(userSearchCriteria.getIsLocked())) {
            predicates.add(criteriaBuilder.equal(userRoot.get("isLocked"), userSearchCriteria.getIsLocked()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(UserPage userPage,
                          CriteriaQuery<User> criteriaQuery,
                          Root<User> userRoot) {
        if(userPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(userPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(userPage.getSortBy())));
        }
    }

    private Pageable getPageable(UserPage userPage) {
        Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
        return PageRequest.of(userPage.getPageNumber(),userPage.getPageSize(), sort);
    }

    private long getPostsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Post> countRoot = countQuery.from(Post.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
