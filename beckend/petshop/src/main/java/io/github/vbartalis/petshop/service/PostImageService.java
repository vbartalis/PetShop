package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.PostImage;
import org.springframework.web.multipart.MultipartFile;

public interface PostImageService {

//    Page<PostImage> getAllPostImages();

//    PostImage createPostImage(PostImage postImageRequest);

    PostImage updatePostImage(long id, MultipartFile multipartFile);

    void deletePostImage(long id);

    PostImage getPostImageById(long id);
}
