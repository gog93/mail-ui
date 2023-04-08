package org.medical.hub.workflow;

//import org.medical.hub.datatable.DataTableRequest;
//import org.medical.hub.datatable.DataTableResponse;
//import org.medical.hub.dto.EmailTemplatesDataTable;
//import org.medical.hub.request.CreateEmailTemplateRequest;

import java.util.List;
import java.util.Optional;

public interface WorkflowService {

    void save(CreateWorkflowRequest request);

//    Workflow saveWithMail(CreateWorkflowRequest request, Long mailId);

    void update(Long ruleId, CreateWorkflowRequest request);

    Optional<Workflow> findById(Long id);

    void delete(Long id);

    List<Workflow> findAll();

//    WDataTableResponse<WorkflowDataTable> getEmailTemplates(DataTableRequest dataTable);

}
