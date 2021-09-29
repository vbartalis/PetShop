package io.github.vbartalis.petshop.repository.criteriaRepository;


import io.github.vbartalis.petshop.dto.user.UserPage;
import io.github.vbartalis.petshop.dto.user.UserSearchCriteria;
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

/**
 * This interface is a Repository for the {@code User} Entity.
 */
@Repository
public class UserCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public UserCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    /**
     * This method finds all Users that match the provided criteria.
     *
     * @param userPage           The properties of the page returned.
     * @param userSearchCriteria The criteria by which the returned page of entities should be filtered.
     * @return Returns a page of {@code User} entities.
     */
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
        long usersCount = getUsersCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, usersCount);
    }

    /**
     * This method is used to create a {@code Predicate} object, that contains a filtering query.
     *
     * @param userSearchCriteria The {@code UserSearchCriteria} object that contains the filtering properties.
     * @param userRoot           The {@code Root<User>} object.
     * @return Returns a {@code Predicate} object, that contains a filtering query.
     */
    private Predicate getPredicate(UserSearchCriteria userSearchCriteria, Root<User> userRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(userSearchCriteria.getId())) {
            predicates.add(criteriaBuilder.equal(userRoot.get("id"), userSearchCriteria.getId()));
        }
        if (Objects.nonNull(userSearchCriteria.getUsername())) {
            predicates.add(criteriaBuilder.like(userRoot.get("username"), "%" + userSearchCriteria.getUsername() + "%"));
        }
        if (Objects.nonNull(userSearchCriteria.getIsLocked())) {
            predicates.add(criteriaBuilder.equal(userRoot.get("isLocked"), userSearchCriteria.getIsLocked()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * This method sets how should the criteriaQuery be ordered by.
     *
     * @param userPage      The {@code UserPage} object, contains what should the criteriaQuery be ordered by.
     * @param criteriaQuery The {@code CriteriaQuery<User>} object.
     * @param userRoot      The {@code Root<User>} object.
     */
    private void setOrder(UserPage userPage,
                          CriteriaQuery<User> criteriaQuery,
                          Root<User> userRoot) {
        if (userPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(userPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(userPage.getSortBy())));
        }
    }

    /**
     * This method creates a {@code Pageable} object from the provided {@code UserPage} object.
     *
     * @param userPage The {@code UserPage} object.
     * @return Returns a {@code Pageable} object.
     */
    private Pageable getPageable(UserPage userPage) {
        Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
        return PageRequest.of(userPage.getPageNumber(), userPage.getPageSize(), sort);
    }

    /**
     * This method returns the number of {@code User} objects filtered by the predicate.
     *
     * @param predicate The {@code Predicate} object, that contains a filtering query.
     * @return Returns the number of {@code User} objects filtered by the predicate.
     */
    private long getUsersCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
