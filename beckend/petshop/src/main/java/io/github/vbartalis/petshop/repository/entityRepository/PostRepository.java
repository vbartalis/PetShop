package io.github.vbartalis.petshop.repository.entityRepository;


import io.github.vbartalis.petshop.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /*-----------------------------------------------------------------------*/
    //Find All Posts By TagIds
    /*-----------------------------------------------------------------------*/
    @Query(" select p from Post p join p.tags t where t.id in ?1 group by p having count(t) = ?2 ")
    Page<Post> findAllByTagsId(List<Long> ids, Long tagCount, Pageable pageable);

    @Query(" select p from Post p join p.tags t where t.id in ?1 group by p having count(t) = ?2 order by p.updateDate")
    Page<Post> findAllByTagsIdOrderByUpdateDate(List<Long> ids, Long tagCount, Pageable pageable);

    @Query(" select p from Post p join p.tags t where t.id in ?1 group by p having count(t) = ?2 order by p.creationDate")
    Page<Post> findAllByTagsIdOrderByCreationDate(List<Long> ids, Long tagCount, Pageable pageable);

    /*-----------------------------------------------------------------------*/
    //Find All Public Posts By TagIds
    /*-----------------------------------------------------------------------*/
    @Query(" select p from Post p join p.tags t where t.id in ?1 and p.isPublic = true group by p having count(t) = ?2 ")
    Page<Post> findAllPublicByTagsId(List<Long> ids, Long tagCount, Pageable pageable);

    @Query(" select p from Post p join p.tags t where t.id in ?1 and p.isPublic = true group by p having count(t) = ?2 order by p.updateDate")
    Page<Post> findAllPublicByTagsIdOrderByUpdateDate(List<Long> ids, Long tagCount, Pageable pageable);

    @Query(" select p from Post p join p.tags t where t.id in ?1 and p.isPublic = true group by p having count(t) = ?2 order by p.creationDate")
    Page<Post> findAllPublicByTagsIdOrderByCreationDate(List<Long> ids, Long tagCount, Pageable pageable);

    /*-----------------------------------------------------------------------*/
    //Find All Public Posts
    /*-----------------------------------------------------------------------*/
    @Query(" select p from Post p where p.isPublic = true")
    Page<Post> findAllPublic(Pageable pageable);

    @Query(" select p from Post p where p.isPublic = true order by p.updateDate")
    Page<Post> findAllPublicOrderByUpdateDate(Pageable pageable);

    @Query(" select p from Post p where p.isPublic = true order by p.creationDate")
    Page<Post> findAllPublicOrderByCreationDate(Pageable pageable);

    /*-----------------------------------------------------------------------*/
    //Find All By User
    /*-----------------------------------------------------------------------*/
    @Query(" select p from Post p join p.user u where u.id in ?1")
    Page<Post> findAllByUser(Long id, Pageable pageable);

    @Query(" select p from Post p join p.user u where u.id in ?1 order by p.updateDate")
    Page<Post> findAllByUserOrderByUpdateDate(Long id, Pageable pageable);

    @Query(" select p from Post p join p.user u where u.id in ?1 order by p.creationDate")
    Page<Post> findAllByUserOrderByCreationDate(Long id, Pageable pageable);

}
