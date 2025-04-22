package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.MailBody;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Role;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.UserRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.AddNewUserService;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddNewUserServiceImpl implements AddNewUserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // Inject EmailService

    @Override
    public User addNewUser(String firstname, String lastname, String fullname, String email, String password, Role role, int epfNo) {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setEpfNo(epfNo);

        User savedUser = userRepo.save(user);

        // Send email with credentials
        String emailContent = "Dear " + fullname + ",\n\n" +
                "You have been added to the TTMS system. Below are your login credentials:\n\n" +
                "Username: " + email + "\n" +
                "Password: " + password + "\n\n" +
                "Please change your password upon first login.\n\n" +
                "Best regards,\nTTMS Team";

        MailBody mailBody = new MailBody(email, "Welcome to TTMS", emailContent);
        emailService.sendSimpleMessage(mailBody);

        return savedUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUserById(String id) {
        userRepo.deleteById(id);
    }

    @Override
    public User updateUser(String id, String firstname, String lastname, String fullname, String email, Role role, int epfNo) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setRole(role);
        user.setEpfNo(epfNo);
        return userRepo.save(user);
    }
}
