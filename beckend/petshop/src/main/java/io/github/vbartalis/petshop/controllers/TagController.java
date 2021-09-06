package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.tag.PatchTagDto;
import io.github.vbartalis.petshop.dto.tag.PostTagDto;
import io.github.vbartalis.petshop.dto.tag.TagDto;
import io.github.vbartalis.petshop.entity.Tag;
import io.github.vbartalis.petshop.security.methodlevel.IsAdmin;
import io.github.vbartalis.petshop.service.TagService;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "create a new Tag", description = "Can be used by Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @PostMapping()
    public TagDto postTag(@RequestBody PostTagDto dto) {
        Tag tag = converter.convertToEntity(dto, Tag.class);
        Tag responseTag = tagService.save(tag);
        return converter.convertToDto(responseTag, TagDto.class);
    }

    @Operation(summary = "get Tag By Tag Id")
    @GetMapping("/{id}")
    public TagDto getTag(@PathVariable("id") Long id) {
        Tag responseTag = tagService.findById(id);
        return converter.convertToDto(responseTag, TagDto.class);
    }

    @Operation(summary = "get All Tags")
    @GetMapping
    public List<TagDto> getTags() {
        List<Tag> tags = tagService.findAll();
        return converter.convertToListDto(tags, TagDto.class);
    }

    @Operation(summary = "update Tag", description = "Can be used by Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @PatchMapping("/{id}")
    public TagDto patchTag(@Valid @RequestBody PatchTagDto dto) {
        Tag tag = converter.convertToEntity(dto, Tag.class);
        Tag responseTag = tagService.patch(tag);
        return converter.convertToDto(responseTag, TagDto.class);
    }

    @Operation(summary = "delete Tag", description = "Can be used by Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Long id) {
        tagService.delete(id);
    }
}