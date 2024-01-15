package vn.codegym.houserental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.codegym.houserental.dto.JwtDTO;
import vn.codegym.houserental.dto.UserDTO;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.account.Role;
import vn.codegym.houserental.model.account.User;
import vn.codegym.houserental.model.account.JwtResponse;
import vn.codegym.houserental.response.ApiResponse;
import vn.codegym.houserental.service.RoleService;
import vn.codegym.houserental.service.UserService;
import vn.codegym.houserental.service.impl.JwtService;
import vn.codegym.houserental.utils.ModelMapperUtil;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Autowired
    private JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasFieldErrors()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (!userService.isCorrectConfirmPassword(user)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (user.getRoles() != null) {
                Set<Role> roles = new HashSet<>();
                roles.add(roleService.findByName("ROLE_ADMIN"));
                user.setRoles(roles);
            } else {
                Set<Role> roles1 = new HashSet<>();
                roles1.add(roleService.findByName("ROLE_USER"));
                user.setRoles(roles1);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            return new ResponseEntity<>(modelMapperUtil.map(user, UserDTO.class), HttpStatus.CREATED);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ApiResponse("01", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("99", "Lỗi hệ thống", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(modelMapperUtil.map(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities()), JwtDTO.class));
    }
}
