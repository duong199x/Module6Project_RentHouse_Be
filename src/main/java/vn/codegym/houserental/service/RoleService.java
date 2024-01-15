package vn.codegym.houserental.service;


import vn.codegym.houserental.model.account.Role;

public interface RoleService {
    Iterable<Role> findAll();

    void save(Role role);

    Role findByName(String name);
}
