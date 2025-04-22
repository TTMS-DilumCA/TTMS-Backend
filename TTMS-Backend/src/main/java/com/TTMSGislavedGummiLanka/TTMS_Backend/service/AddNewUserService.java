package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Role;

import java.util.List;

public interface AddNewUserService {
    User addNewUser(String firstname, String lastname, String fullname, String email, String password, Role role, int epfNo);
    List<User> getAllUsers();
    void deleteUserById(String id);
    User updateUser(String id, String firstname, String lastname, String fullname, String email, Role role, int epfNo);

}
