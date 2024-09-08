-- CREATE Statement
CREATE TABLE flux.report_process_tracker (
	ID VARCHAR(100) NOT NULL,
	TRACE_ID varchar(100) NOT NULL,
	SERVICE_NAME varchar(50) NULL,
	PROCESS_NAME varchar(50) NULL,
	`PATH` varchar(100) NULL,
	CREATED_TIMESTAMP TIMESTAMP NULL,
	CREATED_BY varchar(50) NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

-- ALTER Statement
ALTER TABLE flux.report_process_tracker ADD CONSTRAINT report_process_tracker_pk PRIMARY KEY (ID);