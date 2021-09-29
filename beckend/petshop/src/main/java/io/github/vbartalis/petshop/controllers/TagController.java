package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.tag.TagCreateDto;
import io.github.vbartalis.petshop.dto.tag.TagDto;
import io.github.vbartalis.petshop.dto.tag.TagUpdateDto;
import io.github.vbartalis.petshop.entity.Tag;
import io.github.vbartalis.petshop.security.methodlevel.IsAdmin;
import io.github.vbartalis.petshop.service.impl.TagServiceImpl;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * This class serves as RestController.
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagServiceImpl tagService;
    @Autowired
    DtoEntityConverter converter;

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a GET request.
     * It returns a list of {@code Tag} entities in the form of a {@code TagDto} list.
     *
     * @return Returns a list of {@code Tag} entities in the form of a {@code TagDto} list.
     */
    @Operation(summary = "Get All Tags.")
    @GetMapping
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return converter.convertToListDto(tags, TagDto.class);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a POST request.
     * It creates a new {@code Tag} Entity.
     *
     * @param dto The Data Transfer Object that contains the properties of the new {@code Tag} Entity.
     * @return Returns the created {@code Tag} Entity in the form of {@code TagDto}.
     */
    @Operation(
            summary = "Create a new Tag.",
            description = "Can be used by Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @PostMapping()
    public TagDto createTag(@RequestBody TagCreateDto dto) {
        Tag tag = converter.convertToEntity(dto, Tag.class);
        Tag responseTag = tagService.createTag(tag);
        return converter.convertToDto(responseTag, TagDto.class);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a PUT request.
     * It updates the specified {@code Tag} entity.
     *
     * @param id  the id of the {@code Tag} entity.
     * @param dto The Data Transfer Object that contains the properties of the {@code Tag}
     *            Entity that needs to be updated.
     * @return Returns the updated {@code Tag} Entity in the form of {@code TagDto}.
     */
    @Operation(
            summary = "Update a Tag.",
            description = "Can be used by Admin to update name, description properties.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @PutMapping("/{id}")
    public TagDto updateTag(
            @PathVariable(value = "id") @NotNull Long id,
            @Valid @RequestBody TagUpdateDto dto
    ) {
        Tag tag = converter.convertToEntity(dto, Tag.class);
        Tag responseTag = tagService.updateTag(id, tag);
        return converter.convertToDto(responseTag, TagDto.class);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a DELETE request.
     * It deletes the specified {@code Tag} entity.
     *
     * @param id The id of the {@code Tag} entity.
     */
    @Operation(
            summary = "Delete a Tag.",
            description = "Can be used by Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") @NotNull Long id) {
        tagService.deleteTag(id);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a GET request.
     * It returns the specified {@code Tag} entity in the form of {@code TagDto}.
     *
     * @param id The id of the {@code Tag} entity.
     * @return Returns the specified {@code Tag} Entity in the form of {@code TagDto}.
     */
    @Operation(summary = "Get a Tag By Tag Id.")
    @GetMapping("/{id}")
    public TagDto getTag(@PathVariable("id") @NotNull Long id) {
        Tag responseTag = tagService.getTagById(id);
        return converter.convertToDto(responseTag, TagDto.class);
    }
}