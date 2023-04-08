package org.medical.hub.customer.service;

import org.medical.hub.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CustomerService {

    Customer save(Customer userEmails);
    List<Customer> findAll( );

    Set<Customer> findAllCustomersByWorkflowId(Long id);

    Optional<Customer> findById(Long customerId);

}
