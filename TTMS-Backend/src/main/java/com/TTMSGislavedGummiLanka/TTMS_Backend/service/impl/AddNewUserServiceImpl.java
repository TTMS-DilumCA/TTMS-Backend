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

        // Send email with credentials using HTML formatting
        String emailContent = String.format("""
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h2 style="color: #2c3e50;">Welcome to TTMS</h2>
                    <p>Dear %s,</p>
                    
                    <p>Your account has been successfully created in the TTMS system. Here are your login credentials:</p>
                    
                    <div style="background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;">
                        <p style="margin: 5px 0;"><strong>Username:</strong> %s</p>
                        <p style="margin: 5px 0;"><strong>Password:</strong> %s</p>
                    </div>
                    
                    <p style="color: #e74c3c;"><strong>Important:</strong> For security reasons, please change your password when you first log in.</p>
                    
                    <p>If you have any questions or need assistance, please don't hesitate to contact the IT support team.</p>
                    
                    <p style="margin-top: 30px;">Best regards,<br>TTMS Team</p>
                    
                    <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
                    <p style="font-size: 12px; color: #777;">This is an automated message. Please do not reply to this email.</p>
                </div>
            </body>
            </html>
            """, fullname, email, password);

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
