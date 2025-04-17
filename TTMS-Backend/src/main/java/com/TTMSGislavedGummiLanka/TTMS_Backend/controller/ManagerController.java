package com.TTMSGislavedGummiLanka.TTMS_Backend.controller;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.AddNewUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final AddNewUserService addNewUserService;

    @PostMapping("/add-user")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        // Retrieve the role of the authenticated user from the JWT token
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .findFirst()
                .map(Object::toString)
                .orElse("UNKNOWN");

        System.out.println(">>> /add-user called by user with role: " + currentUserRole);

        // Proceed with adding the new user
        User newUser = addNewUserService.addNewUser(
                user.getFirstname(),
                user.getLastname(),
                user.getFullname(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getEpfNo()
        );
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = addNewUserService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @DeleteMapping("/delete-user/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        addNewUserService.deleteUserById(id); // Now passing String
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-user/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
        User updatedUser = addNewUserService.updateUser(
                id, // Now passing String
                user.getFirstname(),
                user.getLastname(),
                user.getFullname(),
                user.getEmail(),
                user.getRole(),
                user.getEpfNo()
        );
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
