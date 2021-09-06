package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.Role;
import io.github.vbartalis.petshop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
