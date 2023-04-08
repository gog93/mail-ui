package org.medical.hub.customer.controller;

import org.medical.hub.customer.Customer;
import org.medical.hub.customer.repository.CustomerRepository;
import org.medical.hub.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository userEmailRepository) {
        this.customerRepository = userEmailRepository;
    }


    @Override
    public Customer save(Customer userEmails) {
        return customerRepository.save(userEmails);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Set<Customer> findAllCustomersByWorkflowId(Long id) {
        return customerRepository.findAllCustomersByWorkflowId(id);
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }



}
