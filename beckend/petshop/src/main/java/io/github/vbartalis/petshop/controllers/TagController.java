package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.tag.TagDto;
import io.github.vbartalis.petshop.dto.tag.TagUpdateDto;
import io.github.vbartalis.petshop.dto.tag.TagCreateDto;
import io.github.vbartalis.petshop.entity.Tag;
import io.github.vbartalis.petshop.security.methodlevel.IsAdmin;
import io.github.vbartalis.petshop.service.impl.TagServiceImpl;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagServiceImpl tagService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "Get All Tags.")
    @GetMapping
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return converter.convertToListDto(tags, TagDto.class);
    }

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

    @Operation(
            summary = "Delete a Tag.",
            description = "Can be used by Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") @NotNull Long id) {
        tagService.deleteTag(id);
    }

    @Operation(summary = "Get a Tag By Tag Id.")
    @GetMapping("/{id}")
    public TagDto getTag(@PathVariable("id") @NotNull Long id) {
        Tag responseTag = tagService.getTagById(id);
        return converter.convertToDto(responseTag, TagDto.class);
    }
}