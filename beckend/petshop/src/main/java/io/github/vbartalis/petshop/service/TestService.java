package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.dto.request.Filter;
import io.github.vbartalis.petshop.dto.request.Pagination;
import io.github.vbartalis.petshop.dto.test.PostPage;
import io.github.vbartalis.petshop.dto.test.PostSearchCriteria;
import io.github.vbartalis.petshop.entity.Post;
import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.repository.PostRepository;
import io.github.vbartalis.petshop.repository.TestCriteriaRepository;
import io.github.vbartalis.petshop.repository.TestRepository;
import io.github.vbartalis.petshop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class TestService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestCriteriaRepository testCriteriaRepository;

    public Page<User> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        return userRepository.findAll(pageable);
    }

    public Page<Post> getPosts(PostPage postPage, PostSearchCriteria postSearchCriteria) {
        if (Objects.isNull(postPage)) postPage = new PostPage();
        return testCriteriaRepository.findAllWithFilters(postPage, postSearchCriteria);
    }

    public Post addPost(Post post) {
        return testRepository.save(post);
    }


}
