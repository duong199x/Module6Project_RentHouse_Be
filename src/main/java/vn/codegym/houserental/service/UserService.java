package vn.codegym.houserental.service;

import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.account.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void save(User user) throws CommonException;

    Iterable<User> findAll();

    User findByUsername(String username);
    User findByEmail(String email);
    User findByVerificationToken(String token);

    User getCurrentUser();

    Optional<User> findById(Long id);

    UserDetails loadUserById(Long id);

    boolean checkLogin(User user);

    boolean isRegister(User user);

    boolean isCorrectConfirmPassword(User user);

    Iterable<User> searchUserByName(String name);
    String generateVerificationToken(User user);
    boolean activateUserAccount(String token);

}
