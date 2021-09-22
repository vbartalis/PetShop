package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.response.ProfileDto;
import io.github.vbartalis.petshop.entity.Profile;
import io.github.vbartalis.petshop.service.impl.ProfileServiceImpl;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileServiceImpl profileService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "Update a Profile.",
            description = "Can be used by Owner or Admin to update name, email, address, description properties.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkProfile(#dto.id, authentication) || hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ProfileDto updateProfile(
            @PathVariable("id") @NotNull Long id,
            @Valid @RequestBody ProfileDto dto
    ) {
        Profile profile = converter.convertToEntity(dto, Profile.class);
        Profile responseProfile = profileService.updateProfile(id, profile);
        return converter.convertToDto(responseProfile, ProfileDto.class);
    }

    @Operation(summary = "Get a Profile by it's Id.")
    @GetMapping("/{id}")
    public ProfileDto getProfileById(@PathVariable("id") @NotNull Long id) {
        Profile responseProfile = profileService.getProfileById(id);
        return converter.convertToDto(responseProfile, ProfileDto.class);
    }

}
