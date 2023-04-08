package org.medical.hub.workflow;

import lombok.Data;

@Data
public class WorkflowDataTable {

    private Integer sn;
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private String createdBy;
    private String action ;

}
