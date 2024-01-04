package org.example.projectmodule6renthousebe.service.impl;

import org.example.projectmodule6renthousebe.model.account.Role;
import org.example.projectmodule6renthousebe.repository.RoleRepository;
import org.example.projectmodule6renthousebe.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void save(Role role) {
roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }


}
