package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.profileimage.ProfileImageEmptyDto;
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
import java.util.Objects;

/**
 * This class serves as RestController.
 */
@Slf4j
@RestController
@RequestMapping("/profileimage")
public class ProfileImageController {

    @Autowired
    ProfileImageServiceImpl profileImageService;
    @Autowired
    DtoEntityConverter converter;

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a PUT request.
     * It updates a {@code ProfileImage} Entity.
     *
     * @param id            The id of the {@code ProfileImage} entity.
     * @param multipartFile The multipart file that is the actual image.
     * @return Returns the updated {@code ProfileImage} Entity in the form of {@code ProfileImageEmptyDto}.
     */
    @Operation(
            summary = "Update a ProfileImage.",
            description = "Can be used by Owner or Admin to update image property.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkProfileImage(#id) || hasAuthority('ROLE_ADMIN')")
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/{id}")
    public ProfileImageEmptyDto updateProfileImage(
            @PathVariable(value = "id") @NotNull Long id,
            @RequestPart(value = "image") @NotNull MultipartFile multipartFile
    ) {
        ProfileImage response = profileImageService.updateProfileImage(id, multipartFile);
        return converter.convertToDto(response, ProfileImageEmptyDto.class);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a DELETE request.
     * It deletes the data parameter of a {@code ProfileImage} Entity.
     *
     * @param id The id of the {@code ProfileImage} entity.
     */
    @Operation(
            summary = "Delete a ProfileImage.",
            description = "Can be used by Owner or Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkProfileImage(#id) || hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProfileImage(@PathVariable("id") @NotNull Long id) {
        profileImageService.deleteProfileImage(id);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a GET request.
     * It returns the data property of a {@code ProfileImage} Entity as a base64 encoded image.
     *
     * @param id The id of the {@code ProfileImage} entity.
     * @return Returns the data property of a {@code ProfileImage} Entity as a base64 encoded image.
     */
    @Operation(summary = "Get a ProfileImage by it's Id.")
    @GetMapping("/{id}")
    public String getProfileImageById(@PathVariable("id") @NotNull Long id) {
        ProfileImage response = profileImageService.getProfileImageById(id);
        if (Objects.nonNull(response.getData())) {
            return "data:image/jpeg;base64," +
                    StringUtils.newStringUtf8(Base64.encodeBase64(response.getData()));
        } else {
            return null;
        }

    }
}
