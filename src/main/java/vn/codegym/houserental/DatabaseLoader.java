package vn.codegym.houserental;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.codegym.houserental.model.account.Role;
import vn.codegym.houserental.service.RoleService;
import vn.codegym.houserental.service.UserService;
import vn.codegym.houserental.utils.Constants;

@Component
@Transactional
public class DatabaseLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {
        try {

            Iterable<Role> roleList = roleService.findAll();
            if (!roleList.iterator().hasNext()) {
                roleService.save(new Role(Constants.ROLE_ADMIN));
                roleService.save(new Role(Constants.ROLE_USER));
            }

            if (roleService.findByName(Constants.ROLE_ADMIN) == null) {
                roleService.save(new Role(Constants.ROLE_ADMIN));
                logger.info("INSERT ROLE_ADMIN");
            }
            if (roleService.findByName(Constants.ROLE_USER) == null) {
                roleService.save(new Role(Constants.ROLE_USER));
                logger.info("INSERT ROLE_USER");
            }
            logger.info("-------------------------------");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
