package org.example.projectmodule6renthousebe.service;


import org.example.projectmodule6renthousebe.model.account.Role;

public interface RoleService {
    Iterable<Role> findAll();

    void save(Role role);

    Role findByName(String name);
}
