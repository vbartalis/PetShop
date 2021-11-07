package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.Tag;
import io.github.vbartalis.petshop.repository.entityRepository.TagRepository;
import io.github.vbartalis.petshop.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * This is a service class that implements {@code TagService}. Its methods are used to service the {@code TagController}
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    /**
     * This method is a {@code getAllTags} implementation.
     */
    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    /**
     * This method is a {@code createTag} implementation.
     */
    @Override
    public Tag createTag(Tag tagRequest) {
        Tag tag = new Tag(tagRequest.getName(), tagRequest.getDescription());
        tagRepository.save(tag);
        return tag;
    }

    /**
     * This method is a {@code updateTag} implementation.
     */
    @Override
    public Tag updateTag(long id, Tag tagRequest) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag by id " + id + " not found"));

        tag.setName(tagRequest.getName());
        tag.setDescription(tagRequest.getDescription());

        tagRepository.save(tag);
        return tag;
    }

    /**
     * This method is a {@code deleteTag} implementation.
     */
    @Override
    public void deleteTag(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag by id " + id + " not found"));
        tag.getPosts().forEach(post -> post.getTags().remove(tag));
        tagRepository.deleteById(id);
    }

    /**
     * This method is a {@code getTagById} implementation.
     */
    @Override
    public Tag getTagById(long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag by id " + id + " not found"));
    }
}
