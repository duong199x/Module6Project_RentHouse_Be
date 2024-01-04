package org.example.projectmodule6renthousebe.repository;

import org.example.projectmodule6renthousebe.model.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}