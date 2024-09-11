package com.springcavaj.reactive.service;

import com.springcavaj.reactive.dto.ReportFileTrackerDTO;
import com.springcavaj.reactive.dto.ReportProcessFileTrackerDTO;
import com.springcavaj.reactive.dto.ReportProcessTrackerDTO;
import com.springcavaj.reactive.entity.ReportFileTracker;
import com.springcavaj.reactive.entity.ReportProcessTracker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public interface SpringReactiveWebFluxService {

    public Mono<ReportProcessTracker> saveReportProcessTracker(ReportProcessTrackerDTO reportProcessTrackerDTO);
    public Flux<ReportProcessTrackerDTO> getAllReportProcessTrackers();
    public Mono<ReportProcessTrackerDTO> findByReportProcessTrackerTraceId(String traceId);
    public Mono<ReportFileTracker> saveReportFileTracker(ReportFileTrackerDTO reportFileTrackerDTO);
    public Flux<ReportFileTrackerDTO> getAllReportFileTrackers();
    public Mono<ReportFileTrackerDTO> findByReportFileTrackerTraceId(String traceId);
    public Flux<ReportFileTrackerDTO> findReportFileTrackersByProcessId(String reportProcessId);
    public Mono<ReportProcessFileTrackerDTO> findReportProcessAndFileTrackerByProcessId(String reportProcessId);
    public Flux<ReportProcessFileTrackerDTO> getHistoricalRecords();
    public Mono<byte[]> generatePDFForReportProcessFileTracker();
    public Mono<Void> deleteReportFileTracker(BigInteger id);
    public Mono<Void> deleteReportProcessTracker(BigInteger id);
}
