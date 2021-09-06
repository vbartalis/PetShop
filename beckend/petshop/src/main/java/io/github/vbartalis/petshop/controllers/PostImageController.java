package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.postimage.PostImageDto;
import io.github.vbartalis.petshop.entity.PostImage;
import io.github.vbartalis.petshop.service.PostImageService;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/postimage")
public class PostImageController {

    @Autowired
    PostImageService postImageService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "get PostImage by PostImage Id")
    @GetMapping("/{id}")
    public String getImage(@PathVariable("id") @NotNull Long id) {
        PostImage response = postImageService.findById(id);
        byte[] img = response.getData();
        if (img != null) {
            return "data:image/jpeg;base64," +
                    StringUtils.newStringUtf8(Base64.encodeBase64(response.getData()));
        }
        return null;
    }

    @Operation(summary = "update PostImages Data", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPostImage(new Long(#id), authentication) || hasAuthority('ROLE_ADMIN')")
    @PatchMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public PostImageDto patchImage(
            @RequestPart(value = "id") @NotNull String id,
            @RequestPart(value = "image") @NotNull MultipartFile multipartFile) {
        PostImage response =  postImageService.patch(Long.valueOf(id), multipartFile);
        return converter.convertToDto(response, PostImageDto.class);
    }

    @Operation(summary = "delete a PostImages Data", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPostImage(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable("id") Long id) {
        postImageService.deleteData(id);
    }
}
