package org.medical.hub.workflow;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateWorkflowRequest {

    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Description is required.")
    private String description;
}
