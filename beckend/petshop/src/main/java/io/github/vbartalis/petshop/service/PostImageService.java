package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.PostImage;
import io.github.vbartalis.petshop.exception.FileTypeNotSupportedException;
import io.github.vbartalis.petshop.exception.ImageWriterException;
import io.github.vbartalis.petshop.repository.PostImageRepository;
import io.github.vbartalis.petshop.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class PostImageService {

    @Autowired
    PostImageRepository postImageRepository;

    @Autowired
    PostRepository postRepository;

    public PostImage findById(Long id) {
        return postImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post by id " + id + " not found"));
    }

    public PostImage patch(Long postId, MultipartFile multipartFile) {
        PostImage postImage = postImageRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("PostImage by id " + postId + " not found"));

        if (!Objects.equals(multipartFile.getContentType(), "image/jpeg"))
            throw new FileTypeNotSupportedException();

        try {
            postImage.setData(multipartFile.getBytes());
            postImageRepository.save(postImage);
        } catch (IOException e) {
            log.warn("ImageWriterException()");
            throw new ImageWriterException();
        }
        return postImage;
    }

    public void deleteData(Long id) {
        PostImage postImage = postImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("PostImage by id " + id + " not found"));
        postImage.setData(null);
        postImageRepository.save(postImage);
    }
}
