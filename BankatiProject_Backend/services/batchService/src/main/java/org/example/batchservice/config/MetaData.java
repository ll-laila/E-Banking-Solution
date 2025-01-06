package org.example.batchservice.config;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

public class MetaData {
    @Document(collection = "batch_job_execution")
    public class BatchJobExecution {
        @Id
        private String id;
        private String jobName;
        private String status;
        private String exitCode;
        private String exitMessage;
        private Date createTime;
        private Date endTime;
        // getters and setters
    }

    @Document(collection = "batch_step_execution")
    public class BatchStepExecution {
        @Id
        private String id;
        private String stepName;
        private String status;
        private String exitCode;
        private String exitMessage;
        private Date startTime;
        private Date endTime;
        private String jobExecutionId;  // Référence au JobExecution
        // getters and setters
    }

}
