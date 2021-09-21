package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.PostImage;
import io.github.vbartalis.petshop.exception.FileTypeNotSupportedException;
import io.github.vbartalis.petshop.exception.ImageWriterException;
import io.github.vbartalis.petshop.repository.entityRepository.PostImageRepository;
import io.github.vbartalis.petshop.service.PostImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class PostImageServiceImpl implements PostImageService {

    @Autowired
    PostImageRepository postImageRepository;

    @Override
    public PostImage updatePostImage(long id, MultipartFile multipartFile) {
        PostImage postImage = postImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("PostImage by id " + id + " not found"));
        if (!Objects.equals(multipartFile.getContentType(), "image/jpeg"))
            throw new FileTypeNotSupportedException();
        try {
            postImage.setData(multipartFile.getBytes());
            postImageRepository.save(postImage);
        } catch (IOException e) {
            throw new ImageWriterException();
        }
        return postImage;
    }

    @Override
    public void deletePostImage(long id) {
        PostImage postImage = postImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("PostImage by id " + id + " not found"));
        postImage.setData(null);
        postImageRepository.save(postImage);
    }

    @Override
    public PostImage getPostImageById(long id) {
        return postImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post by id " + id + " not found"));
    }
}
