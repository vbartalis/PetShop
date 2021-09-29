package io.github.vbartalis.petshop.repository.entityRepository;

import io.github.vbartalis.petshop.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is a JpaRepository for the {@code ProfileImage} Entity.
 */
@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
