package io.github.vbartalis.petshop.repository.entityRepository;

import io.github.vbartalis.petshop.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
