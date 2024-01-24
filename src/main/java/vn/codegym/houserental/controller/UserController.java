package vn.codegym.houserental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.codegym.houserental.dto.UserDTO;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.account.User;
import vn.codegym.houserental.requests.PasswordRequest;
import vn.codegym.houserental.response.LogoutResponse;
import vn.codegym.houserental.response.VerifyTokenResponse;
import vn.codegym.houserental.service.UserService;
import vn.codegym.houserental.service.impl.JwtService;
import vn.codegym.houserental.utils.ModelMapperUtil;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapperUtil modelMapperUtil;


    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}/users")
    public ResponseEntity<Iterable<User>> showAllUserByAdmin(@PathVariable Long id) {
        Iterable<User> users = userService.findAllByIdNotAndDeleteFlag(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(modelMapperUtil.map(user, UserDTO.class), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable Long id, @RequestBody User user) throws CommonException {
        user.setId(id);
        Optional<User> userOptional = this.userService.findById(id);
        Long authenticatedUserId = this.userService.getCurrentUser().getId();
        if (!authenticatedUserId.equals(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user1 = userOptional.get();
        user1.setFullName(user.getFullName());
        user1.setAddress(user.getAddress());
        user1.setPhone(user.getPhone());
        user1.setAge(user.getAge());
        user1.setDateOfBirth(user.getDateOfBirth());
        user1.setEmail(user.getEmail());
        user1.setImageUser(user.getImageUser());
        userService.saveUpdate(user1);
        return new ResponseEntity<>(modelMapperUtil.map(user, UserDTO.class), HttpStatus.OK);
    }

    @PatchMapping("/users/avatar/{id}")
    public ResponseEntity<UserDTO> updateUserAvatar(@PathVariable Long id, @RequestBody User user) throws CommonException {
        user.setId(id);
        Optional<User> userOptional = this.userService.findById(id);
        Long authenticatedUserId = this.userService.getCurrentUser().getId();
        if (!authenticatedUserId.equals(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user1 = userOptional.get();
        user1.setImageUser(user.getImageUser());
        userService.save(user1);
        return new ResponseEntity<>(modelMapperUtil.map(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/admin/search")
    public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String name) {
        List<User> userList = (List<User>) userService.searchUserByName(name);
        if (userList == null) {
            List<User> users = (List<User>) userService.findAll();
            return new ResponseEntity<>(modelMapperUtil.mapList(users, UserDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(modelMapperUtil.mapList(userList, UserDTO.class), HttpStatus.OK);
        }
    }

    @GetMapping("/users/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity<>(new LogoutResponse("MS-LO-01"), HttpStatus.OK);
    }

    @PatchMapping("/users/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordRequest passwordRequest) throws CommonException {
        User currentUser = userService.getCurrentUser();
        if (passwordEncoder.matches(passwordRequest.getOldPassword(), currentUser.getPassword())) {
            if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String newPassword = passwordEncoder.encode(passwordRequest.getPassword());
            currentUser.setPassword(newPassword);
            currentUser.setConfirmPassword(newPassword);
            userService.saveUpdate(currentUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyTokenResponse> activateAccount(@RequestParam String token) {
        boolean activationResult = userService.activateUserAccount(token);

        if (activationResult) {
            return new ResponseEntity<>(new VerifyTokenResponse("MS-VR-01"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new VerifyTokenResponse("ER-VR-01"), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/users/status/{id}")
    public ResponseEntity<?> registerToHost(@PathVariable Long id){
        try {
            userService.registerHost(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/admin/status/{id}")
    public ResponseEntity<?> acceptToHost(@PathVariable Long id){
        try {
            userService.acceptHost(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("users/{id}")
    public ResponseEntity<?> removeUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
