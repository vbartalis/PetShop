package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.postimage.PostImageEmptyDto;
import io.github.vbartalis.petshop.entity.PostImage;
import io.github.vbartalis.petshop.security.filters.OwnerChecker;
import io.github.vbartalis.petshop.service.impl.PostImageServiceImpl;
import io.github.vbartalis.petshop.util.AuthenticationContext;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * This class serves as RestController.
 */
@RestController
@RequestMapping("/postimage")
public class PostImageController {

    @Autowired
    PostImageServiceImpl postImageService;
    @Autowired
    DtoEntityConverter converter;
    @Autowired
    AuthenticationContext authenticationContext;
    @Autowired
    OwnerChecker ownerChecker;

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a PUT request.
     * It updates a {@code PostImage} Entity.
     *
     * @param id            The id of the {@code PostImage} entity.
     * @param multipartFile The multipart file that is the actual image.
     * @return Returns the updated {@code PostImage} Entity in the form of {@code PostImageEmptyDto}.
     */
    @Operation(
            summary = "Update a PostImage.",
            description = "Can be used by Owner or Admin to update image property.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPostImage(#id) || hasAuthority('ROLE_ADMIN')")
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/{id}")
    public PostImageEmptyDto updatePostImage(
            @PathVariable(value = "id") @NotNull Long id,
            @RequestPart(value = "image") @NotNull MultipartFile multipartFile) {
        PostImage response = postImageService.updatePostImage(id, multipartFile);
        return converter.convertToDto(response, PostImageEmptyDto.class);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a DELETE request.
     * It deletes the data parameter of a {@code PostImage} Entity.
     *
     * @param id The id of the {@code PostImage} entity.
     */
    @Operation(
            summary = "Delete a PostImage.",
            description = "Can be used by Owner or Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPostImage(#id) || hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePostImage(@PathVariable("id") @NotNull Long id) {
        postImageService.deletePostImage(id);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a GET request.
     * It returns the data property of a {@code PostImage} Entity as a base64 encoded image.
     *
     * @param id The id of the {@code PostImage} entity.
     * @return Returns the data property of a {@code PostImage} Entity as a base64 encoded image.
     */
    @Operation(summary = "Get a PostImage by it's Id.")
    @GetMapping("/{id}")
    public String getPostImageById(@PathVariable("id") @NotNull Long id) {
        boolean isOwner = ownerChecker.checkPostImage(id);
        boolean isAdmin = authenticationContext.isAdmin();
        PostImage response = postImageService.getPostImageById(id);
        if (response.getPost().getIsPublic() || isAdmin || isOwner) {
            return "data:image/jpeg;base64,"
                    + StringUtils.newStringUtf8(Base64.encodeBase64(response.getData()));
        } else {
            throw new AccessDeniedException("Access denied to Post by id " + id);
        }
    }
}
