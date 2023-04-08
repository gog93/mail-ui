package org.medical.hub.customer.repository;

import org.medical.hub.customer.SelectedCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectedCustomerRepository extends JpaRepository<SelectedCustomer, Long> {

    SelectedCustomer findByCustomerId(Long customerId);

    List<SelectedCustomer> findByStatus(String selected);
}
