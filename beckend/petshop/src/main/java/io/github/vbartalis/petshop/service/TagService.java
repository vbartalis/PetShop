package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.Tag;

import java.util.List;
/**
 * This interface defines how a service should look. The implementation should service a controller class.
 */
public interface TagService {
    /**
     * This method gets all {@code Tag} entities.
     * @return Returns all {@code Tag} entities.
     */
    List<Tag> getAllTags();

    /**
     *This method creates a {@code Tag} entity.
     * @param tagRequest The properties of the new {@code Tag} entity.
     * @return Returns the created {@code Tag} entity.
     */
    Tag createTag(Tag tagRequest);

    /**
     *This method updates the specified {@code Tag} entity.
     * @param id The id of the {@code Tag} entity.
     * @param tagRequest The properties by which the {@code Tag} entity should be updated.
     * @return Returns the updated {@code Tag} entity.
     */
    Tag updateTag(long id, Tag tagRequest);

    /**
     * This method deletes the specified {@code Tag} entity.
     * @param id The id of the {@code Tag} entity.
     */
    void deleteTag(long id);

    /**
     * This method gets the specified {@code Tag} entity.
     * @param id The id of the {@code Tag} entity.
     * @return Returns the specified {@code Tag} entity.
     */
    Tag getTagById(long id);
}
