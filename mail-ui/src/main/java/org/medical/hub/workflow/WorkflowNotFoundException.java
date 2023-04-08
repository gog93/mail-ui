package org.medical.hub.workflow;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Workflow not found ")
public class WorkflowNotFoundException extends RuntimeException {
}