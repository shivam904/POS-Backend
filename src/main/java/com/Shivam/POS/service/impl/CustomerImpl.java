package com.Shivam.POS.service.impl;

import com.Shivam.POS.modal.Customer;
import com.Shivam.POS.repository.CustomerRepository;
import com.Shivam.POS.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        Customer update= customerRepository.findById(id).orElseThrow(()->new Exception("customer not found"));
        update.setFullName(customer.getFullName());
        update.setPhone(customer.getPhone());
        update.setEmail(customer.getEmail());
        return customerRepository.save(update);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer update= customerRepository.findById(id).orElseThrow(()->new Exception("customer not found"));
        customerRepository.delete(update);
    }

    @Override
    public Customer getCustomer(Long id) throws Exception {

        return customerRepository.findById(id).orElseThrow(()->new Exception("customer not found"));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> searchCustomer(String keyword) {
        return customerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword,keyword);
    }
}
