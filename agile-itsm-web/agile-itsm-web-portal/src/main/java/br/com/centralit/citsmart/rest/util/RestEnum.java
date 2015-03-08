package br.com.centralit.citsmart.rest.util;

public class RestEnum {

    public static final String FORMAT_DB = "DB";
    public static final String FORMAT_OBJ = "OBJ";

    public static final String PARAM_ERROR = "PARAM";
    public static final String INTERNAL_ERROR = "INTERNAL";
    public static final String INPUT_ERROR = "INPUT";
    public static final String SESSION_ERROR = "SESSION";
    public static final String PERMISSION_ERROR = "PERMISSION";

    public static final String PARAM_CONTRACT_ID = "CONTRACT_ID";
    public static final String PARAM_ORIGIN_ID = "ORIGIN_ID";
    public static final String PARAM_REQUEST_ID = "REQUEST_ID";
    public static final String PARAM_INCIDENT_ID = "INCIDENT_ID";
    public static final String PARAM_DEFAULT_DEPTO_ID = "DEFAULT_DEPTO_ID";

    public enum OperationStatus {

        Active("Active"),
        Inactive("Inactive");

        private final String desc;

        private OperationStatus(final String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

    }

    public enum OperationType {

        Sync("Synchronous"),
        Async("Processed");

        private final String desc;

        private OperationType(final String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

    }

    public enum ClassType {
        Java("Java"),
        JavaScript("Java Script");

        private final String desc;

        private ClassType(final String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

    }

    public enum ExecutionStatus {
        NotInitiated("Not initiated"),
        Error("Error"),
        Processed("Processed");

        private final String desc;

        private ExecutionStatus(final String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

    }

}
