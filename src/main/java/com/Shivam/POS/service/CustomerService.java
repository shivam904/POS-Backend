package com.Shivam.POS.service;

import com.Shivam.POS.modal.Customer;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id,Customer customer) throws Exception;
    void deleteCustomer(Long id) throws Exception;
    Customer getCustomer(Long id) throws Exception;
    List<Customer>getAllCustomers();
    List<Customer> searchCustomer(String keyword);

}
