package org.example.projectmodule6renthousebe.repository;
import org.example.projectmodule6renthousebe.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Iterable<User> findByUsernameContaining(String name);
}