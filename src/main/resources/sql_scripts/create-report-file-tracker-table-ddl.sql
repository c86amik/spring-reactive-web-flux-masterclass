-- CREATE Statment
CREATE TABLE flux.report_file_tracker (
	ID varchar(100) NOT NULL,
	REPORT_PROCESS_ID varchar(100) NOT NULL,
	TRACE_ID varchar(100) NOT NULL,
	FILE_NAME varchar(50) NULL,
	FILE_TYPE varchar(10) NULL,
	CREATED_TIMESTAMP TIMESTAMP NULL,
	CREATED_BY varchar(50) NULL
);

--ALTER Statement
ALTER TABLE flux.report_file_tracker ADD CONSTRAINT report_file_tracker_pk PRIMARY KEY (ID);