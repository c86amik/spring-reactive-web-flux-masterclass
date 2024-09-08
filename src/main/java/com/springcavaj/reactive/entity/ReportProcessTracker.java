package com.springcavaj.reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_process_tracker")
public class ReportProcessTracker {

    @Id
    @Column("ID")
    private String id;

    @Column("TRACE_ID")
    private String traceId;

    @Column("SERVICE_NAME")
    private String serviceName;

    @Column("PROCESS_NAME")
    private String processName;

    @Column("PATH")
    private String path;

    @Column("CREATED_BY")
    private String createdBy;

    @Column("CREATED_TIMESTAMP")
    private LocalDateTime createdTimestamp;
}
