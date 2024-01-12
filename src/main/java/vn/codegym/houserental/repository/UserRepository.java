package vn.codegym.houserental.repository;
import vn.codegym.houserental.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByVerificationToken(String email);
    Iterable<User> findByUsernameContaining(String name);
}