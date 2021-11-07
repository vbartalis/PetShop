package io.github.vbartalis.petshop.repository.entityRepository;

import io.github.vbartalis.petshop.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is a JpaRepository for the {@code PostImage} Entity.
 */
@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

}
