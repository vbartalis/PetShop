package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags();

    Tag createTag(Tag tagRequest);

    Tag updateTag(long id, Tag tagRequest);

    void deleteTag(long id);

    Tag getTagById(long id);
}
