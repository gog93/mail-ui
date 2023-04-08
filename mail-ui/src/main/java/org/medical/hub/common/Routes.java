package org.medical.hub.common;

public class Routes {

    private static final String ECRF = "/eCRF";
    private static final String ECRF_REPORT = "/eCRF-report";
    private static final String RULES = "/rules";
    private static final String EXCEL_FILE = "/excel-file";
    private static final String EMAIL_TEMPLATES = "/email-templates";
    private static final String WORKFLOW = "/workflow";
    private static final String MAIL = "/mail";
    private static final String CUSTOMER = "/customer";

    private Routes() {
    }

    public static class ExcelFile {
        public static final String GET = EXCEL_FILE;
        public static final String EXPORT = EXCEL_FILE + "/{id}/export";
        public static final String DOWNLOAD = EXCEL_FILE + "/{id}/download";
        public static final String DELETE = EXCEL_FILE + "/{id}/delete";
        public static final String VALIDATE_DATA = EXCEL_FILE + "/{id}/validate-data";

        public static final String UPLOAD_CLINICAL_DATA_FILE = EXCEL_FILE + "/upload/clinical-data";
        public static final String UPLOAD_SAMPLE_MANIFEST_FILE = EXCEL_FILE + "/upload/sample-manifest";
        public static final String UPLOAD_FACTORS_PRIOR_FILE = EXCEL_FILE + "/upload/factors-prior";
        public static final String UPLOAD_ALL_TOGETHER_FILE = EXCEL_FILE+"/upload/all-together";

        private ExcelFile() {
        }
    }

    public static class ECRFReport {
        public static final String GET = ECRF_REPORT;
        public static final String AJAX_GET = ECRF_REPORT + "/ajax";
        public static final String MASS_ASSIGN = ECRF_REPORT + "/assign";
        public static final String ASSIGN = ECRF_REPORT + "/{surveyTwoId}/assign";
        public static final String DELETE = ECRF_REPORT + "/{surveyTwoId}/delete";
        public static final String DELETE_SELECTED = ECRF_REPORT + "/delete";

        private ECRFReport() {
        }
    }

    public static class ECRF {
        public static final String GET = ECRF;
        public static final String AJAX_GET = ECRF + "/ajax";
        public static final String EDIT = ECRF + "/{surveyTwoId}/edit";
        public static final String DELETE = ECRF + "/{surveyTwoId}/delete";
        public static final String DELETE_SELECTED = ECRF + "/delete";
    }

    public static class Rules {
        public static final String GET = RULES;
        public static final String CREATE = RULES + "/create";
        public static final String STORE = RULES;
        public static final String VIEW = RULES + "/{ruleId}";
        public static final String UPDATE = RULES + "/{ruleId}";
        public static final String AJAX_GET = RULES + "/ajax";
        public static final String DELETE = RULES + "/{ruleId}/delete";
        public static final String EDIT = RULES + "/{ruleId}/edit";

        private Rules() {
        }
    }

    public static class EmailTemplates {
        public static final String GET = EMAIL_TEMPLATES;
        public static final String CREATE = EMAIL_TEMPLATES + "/create";
        public static final String STORE = EMAIL_TEMPLATES;
        public static final String VIEW = EMAIL_TEMPLATES + "/{emailTemplatesId}";
        public static final String UPDATE = EMAIL_TEMPLATES + "/{emailTemplatesId}";
        public static final String AJAX_GET = EMAIL_TEMPLATES + "/ajax";
        public static final String DELETE = EMAIL_TEMPLATES + "/{emailTemplatesId}/delete";
        public static final String EDIT = EMAIL_TEMPLATES + "/{emailTemplatesId}/edit";

        private EmailTemplates() {
        }
    }

    public static class Mail {
        public static final String GET = MAIL;
        public static final String TRASH_MAIL = MAIL + "/trash";
        public static final String VIEW = MAIL + "/{id}";
        public static final String AJAX_GET = MAIL + "/ajax";
        public static final String AJAX_TRASH_GET = MAIL + "/ajax/trash";
        public static final String DELETE = MAIL + "/{id}/delete";
        public static final String FAVORITE = MAIL + "/{id}/favorite";
        public static final String RESTORE = MAIL + "/{id}/restore";
        public static final String CREATE_MAIL = MAIL + "/{id}/create";
        public static final String DELETE_PERMANENTLY = MAIL + "/{id}/delete-permanently";
        public static final String EMAIL_TEMPLATES = MAIL + "/{id}/email-templates";
        public static final String EMAIL_TEMPLATES_MAIL = MAIL + "/{id}/email-templates/{templateId}";

        public static final String ADD_CUSTOMER_DETAILS = "/mail/{mailId}/add-customer";
        public static final String STORE_CUSTOMER_DETAILS = "/mail/{mailId}/add-customer";
        private Mail() {
        }
    }

    public static class Correspondent {
        public static final String GET = CUSTOMER;
        public static final String AJAX_GET = CUSTOMER+"/ajax";
        public static final String ADD = CUSTOMER + "/add";
        public static final String EDIT = CUSTOMER + "/{id}/edit";
        public static final String UPDATE = CUSTOMER + "/{id}";

        private Correspondent() {
        }
    }

    public static class WorkflowEmail {
        public static final String GET = WORKFLOW;
        public static final String CREATE = WORKFLOW + "/{workflowId}/create-email";
        public static final String STORE = WORKFLOW + "/{workflowId}/create-email" ;
        public static final String UPDATE = WORKFLOW + "/{workflowId}/mail/{mailId}/update" ;
        public static final String EDIT = WORKFLOW + "/{workflowId}/mail/{mailId}/edit" ;
        public static final String DELETE = WORKFLOW + "/{workflowId}/mail/{mailId}/delete" ;

        private WorkflowEmail() {
        }
    }

    public static class Workflow {
        public static final String GET = WORKFLOW;
        public static final String CREATE = WORKFLOW + "/create";
        public static final String STORE = WORKFLOW;
        public static final String VIEW = WORKFLOW + "/{workflowId}";
        public static final String UPDATE = WORKFLOW + "/{workflowId}";
        public static final String AJAX_GET = WORKFLOW + "/ajax";
        public static final String DELETE = WORKFLOW + "/{workflowId}/delete";
        public static final String EDIT = WORKFLOW + "/{workflowId}/edit";

        private Workflow() {
        }
    }


}
