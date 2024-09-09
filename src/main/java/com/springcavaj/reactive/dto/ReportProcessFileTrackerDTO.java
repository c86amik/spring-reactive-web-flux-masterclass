package com.springcavaj.reactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportProcessFileTrackerDTO {

    private BigInteger id;
    private String traceId;
    private String serviceName;
    private String processName;
    private String path;
    private String createdBy;
    private LocalDateTime createdTime;
    private List<ReportFileTrackerDTO> reportFileTrackers;
}
