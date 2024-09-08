package com.springcavaj.reactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportFileTrackerDTO {

    private BigInteger id;
    private String reportProcessId;
    private String traceId;
    private String fileName;
    private String fileType;
    private String createdBy;
    private LocalDateTime createdTime;
}
