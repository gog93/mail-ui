package org.medical.hub.workflow;

import org.medical.hub.customer.Customer;
import org.medical.hub.customer.SelectedCustomer;
import org.medical.hub.customer.service.SelectedCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    private Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

    //    private final MailRepository mailRepository;
    private final WorkflowRepository workflowRepository;
    private final SelectedCustomerService selectedCustomerService;
//    private final MailService mailService;
//    private final LoggedinUser loggedinUser;

    @Autowired
    public WorkflowServiceImpl(WorkflowRepository workflowRepository,/*,
                               LoggedinUser loggedinUser,
                               MailService mailService,
                               MailRepository mailRepository*/SelectedCustomerService selectedCustomerService) {
        this.workflowRepository = workflowRepository;
//        this.loggedinUser = loggedinUser;
//        this.mailService = mailService;
//        this.mailRepository = mailRepository;
        this.selectedCustomerService = selectedCustomerService;
    }

    @Override
    public void save(CreateWorkflowRequest request) {

        logger.info("Saving the workflow details. Request: {}", request);
        Workflow workflow = new Workflow();
        workflow.setName(request.getName());
        workflow.setDescription(request.getDescription());
//        workflow.setCreatedAt(System.currentTimeMillis());
//        workflow.setUpdatedAt(System.currentTimeMillis());

        List<SelectedCustomer> selected = selectedCustomerService.findByStatus("selected");
        List<Customer> customer=new ArrayList<>();

        for (SelectedCustomer selected1: selected){
           customer.add(selected1.getCustomer());
        }
        workflow.setCustomer(customer);
//        User user = this.loggedinUser.currentLoginUser();
//        workflow.setUser(user);

        workflowRepository.save(workflow);
        logger.info("Workflow details saved successfully.");
    }

//    @Transactional
//    @Override
//    public Workflow saveWithMail(CreateWorkflowRequest request, Long mailId) {
//        logger.info("Saving the workflow details. Request: {}", request);
//        Workflow workflow = new Workflow();
//        workflow.setName(request.getName());
//        workflow.setDescription(request.getDescription());
//        workflow.setCreatedAt(System.currentTimeMillis());
//        workflow.setUpdatedAt(System.currentTimeMillis());
//
//        User user = this.loggedinUser.currentLoginUser();
//        workflow.setUser(user);
//
//        workflowRepository.save(workflow);
//        logger.info("Workflow details saved successfully. Adding workflow to mail");
//
//        var mail = this.mailService.getMailDetails(mailId);
//        if(mail == null){
//            // throw exception
//        }
//
//        mail.setWorkflow(workflow);
//        this.mailRepository.save(mail);
//
//        return workflow;
//    }

    @Override
    public void update(Long ruleId, CreateWorkflowRequest request) {

        logger.info("Updating the workflow details. Request: {}", request);
        Workflow workflow = findById(ruleId).get();
        workflow.setName(request.getName());
        workflow.setDescription(request.getDescription());
//        workflow.setUpdatedAt(System.currentTimeMillis());
        List<SelectedCustomer> selected = selectedCustomerService.findByStatus("selected");

        List<Customer> customer=new ArrayList<>();

        for (SelectedCustomer selected1: selected){
            customer.add(selected1.getCustomer());
        }
        workflow.setCustomer(customer);
        workflowRepository.save(workflow);
        logger.info("Workflow details updated successfully.");
    }

    @Override
    public Optional<Workflow> findById(Long workflowId) {
        logger.info("Getting the workflow details. Workflow workflowId: {}", workflowId);
        return workflowRepository.findById(workflowId);
    }

    @Override
    public void delete(Long workflowId) {
        logger.info("Deleting the workflow details having id: {}", workflowId);
        workflowRepository.deleteById(workflowId);
        logger.info("Workflow deleted having id: {}", workflowId);
    }

    @Override
    public List<Workflow> findAll() {
        return workflowRepository.findAll();
    }

//    @Override
//    public DataTableResponse<WorkflowDataTable> getEmailTemplates(DataTableRequest dataTable) {
//        WorkflowSpecification specification = new WorkflowSpecification();
//        String value = dataTable.getSearch().getValue();
//        if (StringUtils.isNotBlank(value)) {
//            var criteria = new SearchCriteria("patID", value, SearchOperation.LIKE);
//            specification.add(criteria);
//        }
//
//        DataTableOrder order = dataTable.getOrder().stream().findFirst().orElse(null);
//
//        Pageable sortedByName = PageRequest.of(dataTable.getStart(), dataTable.getLength());
//        if (order != null) {
//            DataTableColumnSpecs dataTableColumnSpecs = dataTable.getColumns().get(order.getColumn());
//            if (dataTableColumnSpecs != null) {
//                String data = dataTableColumnSpecs.getData();
//                if (data.equals("sn") || data.equals("action")) {
//                    data = "id";
//                }
//                Sort.Direction dir = (order.getDir().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
//                Sort by = Sort.by(new Sort.Order(dir, data));
//                int pageNumber = DataTableUtils.getPageNumber(dataTable);
//                sortedByName = PageRequest.of(pageNumber, dataTable.getLength(), by);
//            }
//        }
//
//        Page<Workflow> all = workflowRepository.findAll(specification, sortedByName);
//        List<WorkflowDataTable> dataTablesData = mapRuleData(all.getContent(), dataTable);
//        long totalRecord = workflowRepository.count();
//        long filteredRecord = workflowRepository.count(specification);
//        DataTableResponse<WorkflowDataTable> response = new DataTableResponse<>();
//        response.setData(dataTablesData);
//        response.setDraw(dataTable.getDraw());
//        response.setRecordsFiltered(filteredRecord);
//        response.setRecordsTotal(totalRecord);
//
//        return response;
//    }

//    private List<WorkflowDataTable> mapRuleData(List<Workflow> workflows, DataTableRequest dataTableRequest) {
//        List<WorkflowDataTable> dataTablesData = new ArrayList<>();
//        int i = 1;
//        for (Workflow workflow : workflows) {
//
//            WorkflowDataTable workflowDataTable = new WorkflowDataTable();
//
//            workflowDataTable.setSn(getSN(dataTableRequest, i));
//            workflowDataTable.setDescription(workflow.getDescription());
//            workflowDataTable.setName(workflow.getName());
//
//            String fullName = workflow.getUser().getFirstName() + " " + workflow.getUser().getSurname();
//            workflowDataTable.setCreatedBy(fullName);
//
//            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(workflow.getCreatedAt());
//            workflowDataTable.setCreatedAt(format);
//            workflowDataTable.setAction(String.valueOf(workflow.getId()));
//            dataTablesData.add(workflowDataTable);
//
//            i++;
//        }
//
//        return dataTablesData;
//    }

//    private Integer getSN(DataTableRequest dataTable, int i) {
//        return dataTable.getStart() + i;
//    }
}
