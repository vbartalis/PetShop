package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.profile.PatchProfileDto;
import io.github.vbartalis.petshop.dto.profile.ProfileDto;
import io.github.vbartalis.petshop.entity.Profile;
import io.github.vbartalis.petshop.service.ProfileService;
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
    ProfileService profileService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "get Profile by Profile Id")
    @GetMapping("/{id}")
    public ProfileDto getProfileById(@PathVariable("id") @NotNull Long id) {
        Profile responseProfile = profileService.findById(id);
        return converter.convertToDto(responseProfile, ProfileDto.class);
    }

    @Operation(summary = "get Profile by User Id")
    @GetMapping("/user/{id}")
    public ProfileDto getProfileByUserId(@PathVariable("id") @NotNull Long id) {
        Profile responseProfile = profileService.findByUserId(id);
        return converter.convertToDto(responseProfile, ProfileDto.class);
    }

    @Operation(summary = "update Profile", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkProfile(#dto.id, authentication) || hasAuthority('ROLE_ADMIN')")
    @PatchMapping
    public ProfileDto patchProfile(@Valid @RequestBody PatchProfileDto dto) {
        Profile profile = converter.convertToEntity(dto, Profile.class);
        Profile responseProfile = profileService.patch(profile);
        return converter.convertToDto(responseProfile, ProfileDto.class);
    }
}
