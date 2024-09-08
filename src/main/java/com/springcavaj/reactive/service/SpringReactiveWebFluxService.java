package com.springcavaj.reactive.service;

import com.springcavaj.reactive.dto.ReportProcessTrackerDTO;
import com.springcavaj.reactive.entity.ReportProcessTracker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface SpringReactiveWebFluxService {

    public Mono<ReportProcessTracker> saveReportProcessTracker(ReportProcessTrackerDTO reportProcessTrackerDTO);

    public Flux<ReportProcessTrackerDTO> getAllReportProcessTrackers();

    public Mono<ReportProcessTracker> findByTraceId(String traceId);



}
