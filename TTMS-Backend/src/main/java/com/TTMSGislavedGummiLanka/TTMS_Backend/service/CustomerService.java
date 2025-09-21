package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(String id);
    Customer addCustomer(Customer customer);
    Customer updateCustomer(String id, Customer customer);
    void deleteCustomer(String id);
}