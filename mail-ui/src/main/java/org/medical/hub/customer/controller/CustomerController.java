package org.medical.hub.customer.controller;


import lombok.RequiredArgsConstructor;
import org.medical.hub.customer.Customer;
import org.medical.hub.customer.SelectedCustomer;
import org.medical.hub.customer.service.CustomerService;
import org.medical.hub.customer.service.SelectedCustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class CustomerController {
    private final CustomerService customerService;
    private final SelectedCustomerService selectedService;

    @GetMapping("/customers")
    public @ResponseBody
    List<Customer> findAllTeacherInCourseById() {
        List<Customer> allCustomer = new ArrayList<>();
        List<Customer> allSelected = new ArrayList<>();
        List<SelectedCustomer> customerInSelected=  selectedService.findAll();
        for (SelectedCustomer selected: customerInSelected){
            allSelected.add(selected.getCustomer());
        }
        for (Customer customer : customerService.findAll()) {
            if (!allSelected.contains(customer)){
                selectedService.save(new SelectedCustomer(1L, "unselected", customer));
                allCustomer.add(customer);
            }

        }
        List<SelectedCustomer> unselected = selectedService.findByStatus("unselected");
        for (SelectedCustomer u: unselected) {
            if (!allCustomer.contains(u.getCustomer())) {
                allCustomer.add(u.getCustomer());
            }
        }
        return allCustomer;
    }

    @PostMapping("/workflow/create/addCustomer")
    @ResponseBody
    public ResponseEntity<Void> addCustomerToWorkflow( @RequestParam Long customerId) {
        selectedService.update(customerId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/workflow/remove/{customerId}")
    @ResponseBody
    public ResponseEntity<Void> removeCustomerToWorkflow( @PathVariable Long customerId) {
        selectedService.update(customerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/workflow/create/")
    public @ResponseBody
    List<Customer> findAllCustomerInWorkflowById() {
        List<Customer> allCustomer = selectedService.findAll().stream()
                .filter(selected -> "selected".equals(selected.getStatus()))
                .map(SelectedCustomer::getCustomer)
                .collect(Collectors.toList());

        return allCustomer;
    }

}

