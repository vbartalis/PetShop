package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.Role;

import java.util.List;

/**
 * This interface defines how a service should look. The implementation should service a controller class.
 */
public interface RoleService {

    /**
     * This method gets all {@code Role} entities.
     *
     * @return Returns all {@code Role} entities.
     */
    List<Role> getAllRoles();
}
