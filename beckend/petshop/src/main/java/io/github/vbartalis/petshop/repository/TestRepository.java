package io.github.vbartalis.petshop.repository;

import io.github.vbartalis.petshop.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Post, Long> {

}
