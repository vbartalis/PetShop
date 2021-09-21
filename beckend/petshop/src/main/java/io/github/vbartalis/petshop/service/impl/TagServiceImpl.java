package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.Post;
import io.github.vbartalis.petshop.entity.Tag;
import io.github.vbartalis.petshop.repository.entityRepository.TagRepository;
import io.github.vbartalis.petshop.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag createTag(Tag tagRequest) {
        Tag tag = new Tag(tagRequest.getName(), tagRequest.getDescription());
        tagRepository.save(tag);
        return tag;
    }

    @Override
    public Tag updateTag(long id, Tag tagRequest) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag by id " + id + " not found"));

        tag.setName(tagRequest.getName());
        tag.setDescription(tagRequest.getDescription());

        tagRepository.save(tag);
        return tag;
    }

    @Override
    public void deleteTag(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag by id " + id + " not found"));
        tag.getPosts().forEach(post -> post.getTags().remove(tag));
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getTagById(long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag by id " + id + " not found"));
    }
}
