package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.profileimage.EmptyProfileImageDto;
import io.github.vbartalis.petshop.entity.ProfileImage;
import io.github.vbartalis.petshop.service.ProfileImageService;
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
    ProfileImageService profileImageService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "get ProfileImage By ProfileImage Id")
    @GetMapping("/{id}")
    public String getImage(@PathVariable("id") @NotNull Long id) {
        ProfileImage response = profileImageService.findById(id);
        byte[] img = response.getData();
        if (img != null) {
        return "data:image/jpeg;base64," +
                StringUtils.newStringUtf8(Base64.encodeBase64(response.getData()));
        }
        return null;
    }

    @Operation(summary = "update ProfileImage Data", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkProfileImage(new Long(#id), authentication) || hasAuthority('ROLE_ADMIN')")
    @PatchMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public EmptyProfileImageDto patchImage(
            @RequestPart(value = "id") @NotNull String id,
            @RequestPart(value = "image") @NotNull MultipartFile multipartFile
            ) {
        ProfileImage response = profileImageService.patch(Long.valueOf(id), multipartFile);
        return converter.convertToDto(response, EmptyProfileImageDto.class);
    }

    @Operation(summary = "delete ProfileImage Data", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkProfileImage(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable("id") Long id) {
        profileImageService.deleteData(id);
    }
}
