package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.profileimage.EmptyProfileImageDto;
import io.github.vbartalis.petshop.entity.ProfileImage;
import io.github.vbartalis.petshop.service.impl.ProfileImageServiceImpl;
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
@RequestMapping("/profileimage")
public class ProfileImageController {

    @Autowired
    ProfileImageServiceImpl profileImageService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "Update ProfileImage.",
            description = "Can be used by Owner or Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkProfileImage(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, value = "/{id}")
    public EmptyProfileImageDto updateProfileImage(
            @PathVariable(value = "id") @NotNull Long id,
            @RequestPart(value = "image") @NotNull MultipartFile multipartFile
    ) {
        ProfileImage response = profileImageService.updateProfileImage(id, multipartFile);
        return converter.convertToDto(response, EmptyProfileImageDto.class);
    }

    @Operation(summary = "Delete ProfileImage.",
            description = "Can be used by Owner or Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkProfileImage(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProfileImage(@PathVariable("id") @NotNull Long id) {
        profileImageService.deleteProfileImage(id);
    }

    @Operation(summary = "Get ProfileImage by it's Id.")
    @GetMapping("/{id}")
    public String getProfileImageById(@PathVariable("id") @NotNull Long id) {
        ProfileImage response = profileImageService.getProfileImageById(id);
        byte[] img = response.getData();
        if (img != null) {
            return "data:image/jpeg;base64," +
                    StringUtils.newStringUtf8(Base64.encodeBase64(response.getData()));
        }
        return null;
    }
}
