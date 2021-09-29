package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.Role;
import io.github.vbartalis.petshop.repository.entityRepository.RoleRepository;
import io.github.vbartalis.petshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is a service class that implements {@code RoleService}. Its methods are used to service the {@code RoleController}
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    /**
     * This method is a {@code getAllRoles} implementation.
     */
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
