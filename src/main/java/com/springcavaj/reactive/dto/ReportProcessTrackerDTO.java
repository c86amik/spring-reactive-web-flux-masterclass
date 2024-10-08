package com.springcavaj.reactive.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportProcessTrackerDTO {

    private BigInteger id;
    private String traceId;
    private String serviceName;
    private String processName;
    private String path;
    private String createdBy;
    private LocalDateTime createdTime;
}
