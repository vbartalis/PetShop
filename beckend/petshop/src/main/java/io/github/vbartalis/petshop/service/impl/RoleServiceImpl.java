package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.Role;
import io.github.vbartalis.petshop.repository.entityRepository.RoleRepository;
import io.github.vbartalis.petshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
