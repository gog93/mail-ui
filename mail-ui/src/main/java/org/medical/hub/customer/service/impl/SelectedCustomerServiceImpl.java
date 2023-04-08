package org.medical.hub.customer.service.impl;

import lombok.RequiredArgsConstructor;
import org.medical.hub.customer.SelectedCustomer;
import org.medical.hub.customer.repository.SelectedCustomerRepository;
import org.medical.hub.customer.service.SelectedCustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SelectedCustomerServiceImpl implements SelectedCustomerService {
    private final SelectedCustomerRepository selectedCustomerRepository;

    public SelectedCustomer save(SelectedCustomer customer) {

        return selectedCustomerRepository.save(customer);
    }

    public void delete() {
        selectedCustomerRepository.deleteAll();
    }


    public List<SelectedCustomer> findAll() {
        return selectedCustomerRepository.findAll();
    }


    public void update(Long customerId) {
        SelectedCustomer selected = selectedCustomerRepository.findByCustomerId(customerId);
        selected.setStatus("selected");
        selectedCustomerRepository.save(selected);
    }


    public List<SelectedCustomer> findByStatus(String selected) {
        return selectedCustomerRepository.findByStatus(selected);
    }
}
