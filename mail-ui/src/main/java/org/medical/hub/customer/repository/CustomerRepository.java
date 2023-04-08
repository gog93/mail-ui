package org.medical.hub.customer.repository;


import org.medical.hub.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Set<Customer> findAllCustomersByWorkflowId(Long id);
}
