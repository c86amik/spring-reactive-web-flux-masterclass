package com.springcavaj.reactive.service.impl;

import com.springcavaj.reactive.dto.ReportProcessTrackerDTO;
import com.springcavaj.reactive.entity.ReportProcessTracker;
import com.springcavaj.reactive.repository.SpringReactiveWebFluxRepository;
import com.springcavaj.reactive.service.SpringReactiveWebFluxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SpringReactiveWebFluxServiceImpl implements SpringReactiveWebFluxService {
    private  static Logger LOG = LoggerFactory.getLogger(SpringReactiveWebFluxServiceImpl.class);

    @Autowired
    private SpringReactiveWebFluxRepository springReactiveWebFluxRepository;

    @Override
    public Mono<ReportProcessTracker> saveReportProcessTracker(ReportProcessTrackerDTO reportProcessTrackerDTO) {
        ReportProcessTracker reportProcessTracker = new ReportProcessTracker();
        reportProcessTracker.setTraceId(UUID.randomUUID().toString());
        reportProcessTracker.setProcessName(reportProcessTrackerDTO.getProcessName());
        reportProcessTracker.setServiceName(reportProcessTrackerDTO.getServiceName());
        reportProcessTracker.setPath(reportProcessTrackerDTO.getPath());
        reportProcessTracker.setCreatedBy(reportProcessTrackerDTO.getCreatedBy());
        reportProcessTracker.setCreatedTimestamp(LocalDateTime.now());
        return springReactiveWebFluxRepository.save(reportProcessTracker);
    }

    @Override
    public Flux<ReportProcessTrackerDTO> getAllReportProcessTrackers() {
        Flux<ReportProcessTracker> reportProcessTrackerFlux = springReactiveWebFluxRepository.findAll();
        Flux<ReportProcessTrackerDTO> reportProcessTrackerDTOFlux = reportProcessTrackerFlux
                .flatMap(reportProcessTracker -> convertToReportProcessTrackerDTO(reportProcessTracker));
        return reportProcessTrackerDTOFlux;
    }

    private Mono<ReportProcessTrackerDTO> convertToReportProcessTrackerDTO(ReportProcessTracker processTracker) {
        ReportProcessTrackerDTO reportProcessTrackerDTO = new ReportProcessTrackerDTO();
        reportProcessTrackerDTO.setId(processTracker.getId());
        reportProcessTrackerDTO.setTraceId(processTracker.getTraceId());
        reportProcessTrackerDTO.setServiceName(processTracker.getServiceName());
        reportProcessTrackerDTO.setPath(processTracker.getPath());
        reportProcessTrackerDTO.setCreatedBy(processTracker.getCreatedBy());
        reportProcessTrackerDTO.setCreatedTime(processTracker.getCreatedTimestamp());
        return Mono.just(reportProcessTrackerDTO);
    }

    @Override
    public Mono<ReportProcessTracker> findByTraceId(String traceId) {
        return null;
    }


}
