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

@Controller
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

    @PostMapping("/workflows/{id}/addCustomer")
    @ResponseBody
    public ResponseEntity<Void> addCustomerToWorkflow(@PathVariable("id") Long id, @RequestParam Long customerId) {
        selectedService.update(customerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/workflows/{id}")
    public @ResponseBody
    List<Customer> findAllCustomerInWorkflowById(@PathVariable("id") Long id) {
        List<Customer> allCustomer = new ArrayList<>();
        for (SelectedCustomer selected : selectedService.findAll()) {
            if (("selected").equals(selected.getStatus())) {
                allCustomer.add(selected.getCustomer());
            }
        }

        return allCustomer;
    }

}

