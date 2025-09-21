package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Customer;
import com.TTMSGislavedGummiLanka.TTMS_Backend.exception.UserNotFoundException;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.CustomerRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public Customer updateCustomer(String id, Customer customer) {
        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setFullname(customer.getFullname());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setContactNumber(customer.getContactNumber());
        existingCustomer.setCompany(customer.getCompany());
        existingCustomer.setAddress(customer.getAddress());
        // Update other fields as needed

        return customerRepo.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(String id) {
        Customer customer = getCustomerById(id);
        customerRepo.delete(customer);
    }
}