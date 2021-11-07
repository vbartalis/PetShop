package io.github.vbartalis.petshop.repository.entityRepository;

import io.github.vbartalis.petshop.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is a JpaRepository for the {@code Post} Entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
