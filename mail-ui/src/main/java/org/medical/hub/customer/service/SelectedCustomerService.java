package org.medical.hub.customer.service;

import org.medical.hub.customer.SelectedCustomer;

import java.util.List;

public interface SelectedCustomerService {
    void delete();
    List<SelectedCustomer> findAll();
    List<SelectedCustomer> findByStatus(String selected);
    SelectedCustomer save(SelectedCustomer customer);
    void update(Long customerId);

}
