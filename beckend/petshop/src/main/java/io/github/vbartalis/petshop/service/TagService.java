package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.Post;
import io.github.vbartalis.petshop.entity.Tag;
import io.github.vbartalis.petshop.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public Tag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag by id " + id + " not found"));
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag save(Tag requestDto){
        Tag tag = new Tag(requestDto.getName(), requestDto.getDescription());
        tagRepository.save(tag);
        return tag;
    }

    public Tag patch(Tag requestTag) {
        Tag tag = tagRepository.findById(requestTag.getId())
                .orElseThrow(() -> new NoSuchElementException("Tag by id " + requestTag.getId() + " not found"));
        if (requestTag.getName() != null)
            tag.setName(requestTag.getName());
        if (requestTag.getDescription() != null)
            tag.setDescription(requestTag.getDescription());
        tagRepository.save(tag);
        return tag;
    }

    public void delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag by id " + id + " not found"));
        for (Post post : tag.getPosts()) {
            post.getTags().remove(tag);
        }
        tagRepository.deleteById(id);
    }
}
