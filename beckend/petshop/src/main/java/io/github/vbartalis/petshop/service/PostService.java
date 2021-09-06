package io.github.vbartalis.petshop.service;


import io.github.vbartalis.petshop.entity.Post;
import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.repository.PostRepository;
import io.github.vbartalis.petshop.repository.UserRepository;
import io.github.vbartalis.petshop.util.AuthenticationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationContext authenticationContext;


    public Post save(Post request) {
        var username = authenticationContext.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User by username " + username + " not found"));
        var title = request.getTitle();
        var description = request.getDescription();
        var creationDate = new Date();
        var isPublic = request.getIsPublic();
        var tags = request.getTags();
        Post post = new Post(title, description, user, creationDate, isPublic, tags);

        postRepository.save(post);
        return post;
    }

    public Post findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post by id " + id + " not found"));
        if (post.getIsPublic())
            return post;

        String postUsername = post.getUser().getUsername();
        String username = authenticationContext.getUsername();

        if (username.equals(postUsername))
            return post;
        else if (authenticationContext.isAdmin())
            return post;
        else
            throw new AccessDeniedException("Access is forbidden for user to Post by id " + id);
//            throw new NoSuchElementException("Post by id " + id + " not found(2)");
    }

    public Page<Post> findAllPublicByCriteria(List<Long> ids, int page, int pageSize) {
        Optional<List<Long>> optionalTagIds = Optional.ofNullable(ids);
        Pageable pageable = PageRequest.of(page-1, pageSize);
        if (optionalTagIds.isEmpty()) {
//            return postRepository.findAllPublicOrderByCreationDate(pageable);
            return postRepository.findAllPublicOrderByUpdateDate(pageable);
        } else {
            List<Long> tagIds = optionalTagIds.get();
//            return postRepository.findAllPublicByTagsIdOrderByCreationDate(tagIds, (long) tagIds.size(), pageable);
            return postRepository.findAllPublicByTagsIdOrderByUpdateDate(tagIds, (long) tagIds.size(), pageable);
        }
    }

    public Page<Post> findByUser(Long id, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
//        return postRepository.findAllByUserOrderByCreationDate(id, pageable);
        return postRepository.findAllByUserOrderByUpdateDate(id, pageable);
    }

    public Post patch(Post request) {
        Post post = postRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementException("Post by id " + request.getId() + " not found"));

        if (request.getTitle() != null)
            post.setTitle(request.getTitle());
        if (request.getDescription() != null)
            post.setDescription(request.getDescription());
        if (request.getIsPublic() != null)
            post.setIsPublic(request.getIsPublic());

        if (request.getTags() != null) {
            post.getTags().clear();
            post.setTags(request.getTags());
        }

        post.setUpdateDate(new Date());
        postRepository.save(post);
        return post;
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post by id " + id + " not found"));
        postRepository.delete(post);
    }



}
