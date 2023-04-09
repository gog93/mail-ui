package org.medical.hub.workflow;

import lombok.Getter;
import lombok.Setter;
import org.medical.hub.customer.Customer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CreateWorkflowRequest {

    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Description is required.")
    private String description;
}
