package io.github.vbartalis.petshop.repository.entityRepository;

import io.github.vbartalis.petshop.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is a JpaRepository for the {@code Profile} Entity.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
