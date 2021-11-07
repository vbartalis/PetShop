package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.profile.ProfileDto;
import io.github.vbartalis.petshop.dto.profile.ProfileUpdateDto;
import io.github.vbartalis.petshop.entity.Profile;
import io.github.vbartalis.petshop.service.impl.ProfileServiceImpl;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * This class serves as RestController.
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileServiceImpl profileService;
    @Autowired
    DtoEntityConverter converter;

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a PATCH request.
     * It updates the specified {@code Profile} entity.
     *
     * @param id  The id of the {@code Profile} entity.
     * @param dto The Data Transfer Object that contains the properties of the {@code Profile}
     *            Entity that needs to be updated.
     * @return Returns the updated {@code Profile} Entity in the form of {@code ProfileDto}.
     */
    @Operation(
            summary = "Update a Profile.",
            description = "Can be used by Owner or Admin to update name, email, address, description properties.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkProfile(#id) || hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ProfileDto updateProfile(
            @PathVariable("id") @NotNull Long id,
            @Valid @RequestBody ProfileUpdateDto dto
    ) {
        Profile profile = converter.convertToEntity(dto, Profile.class);
        Profile responseProfile = profileService.updateProfile(id, profile);
        return converter.convertToDto(responseProfile, ProfileDto.class);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a PATCH request.
     * It returns the specified {@code Profile} entity.
     *
     * @param id The id of the {@code Profile} entity.
     * @return Returns the specified {@code Profile} Entity in the form of {@code ProfileDto}.
     */
    @Operation(summary = "Get a Profile by it's Id.")
    @GetMapping("/{id}")
    public ProfileDto getProfileById(@PathVariable("id") @NotNull Long id) {
        Profile responseProfile = profileService.getProfileById(id);
        return converter.convertToDto(responseProfile, ProfileDto.class);
    }

}
