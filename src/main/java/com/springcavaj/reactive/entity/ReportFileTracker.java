package com.springcavaj.reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_file_tracker")
public class ReportFileTracker {

    @Id
    @Column("ID")
    private BigInteger id;

    @Column("REPORT_PROCESS_ID")
    private String reportProcessId;

    @Column("TRACE_ID")
    private String traceId;

    @Column("FILE_NAME")
    private String fileName;

    @Column("FILE_TYPE")
    private String fileType;

    @Column("CREATED_BY")
    private String createdBy;

    @Column("CREATED_TIMESTAMP")
    private LocalDateTime createdTimestamp;
}
