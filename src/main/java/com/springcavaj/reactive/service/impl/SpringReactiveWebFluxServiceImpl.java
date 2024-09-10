package com.springcavaj.reactive.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcavaj.reactive.dto.ReportFileTrackerDTO;
import com.springcavaj.reactive.dto.ReportProcessFileTrackerDTO;
import com.springcavaj.reactive.dto.ReportProcessTrackerDTO;
import com.springcavaj.reactive.entity.ReportFileTracker;
import com.springcavaj.reactive.entity.ReportProcessTracker;
import com.springcavaj.reactive.repository.SpringReactiveWebFluxFileTrackerRepository;
import com.springcavaj.reactive.repository.SpringReactiveWebFluxProcessTrackerRepository;
import com.springcavaj.reactive.service.SpringReactiveWebFluxService;
import com.springcavaj.reactive.utility.ReportProcessFilePDFGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SpringReactiveWebFluxServiceImpl implements SpringReactiveWebFluxService {
    private  static Logger LOG = LoggerFactory.getLogger(SpringReactiveWebFluxServiceImpl.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SpringReactiveWebFluxProcessTrackerRepository springReactiveWebFluxProcessTrackerRepository;

    @Autowired
    private SpringReactiveWebFluxFileTrackerRepository springReactiveWebFluxFileTrackerRepository;

    @Autowired
    private ReportProcessFilePDFGenerator reportProcessFilePDFGenerator;

    @Override
    public Mono<ReportProcessTracker> saveReportProcessTracker(ReportProcessTrackerDTO reportProcessTrackerDTO) {
        ReportProcessTracker reportProcessTracker = new ReportProcessTracker();
        if(null != reportProcessTrackerDTO.getId()) {
            reportProcessTracker.setId(reportProcessTrackerDTO.getId());
        }
        if (StringUtils.hasText(reportProcessTrackerDTO.getTraceId())) {
            reportProcessTracker.setTraceId(reportProcessTrackerDTO.getTraceId());
        } else {
            reportProcessTracker.setTraceId(UUID.randomUUID().toString());
        }
        reportProcessTracker.setProcessName(reportProcessTrackerDTO.getProcessName());
        reportProcessTracker.setServiceName(reportProcessTrackerDTO.getServiceName());
        reportProcessTracker.setPath(reportProcessTrackerDTO.getPath());
        reportProcessTracker.setCreatedBy(reportProcessTrackerDTO.getCreatedBy());
        reportProcessTracker.setCreatedTimestamp(
                (null != reportProcessTrackerDTO.getCreatedTime() ? reportProcessTrackerDTO.getCreatedTime()
                        : LocalDateTime.now()));
        return springReactiveWebFluxProcessTrackerRepository.save(reportProcessTracker);
    }

    @Override
    public Flux<ReportProcessTrackerDTO> getAllReportProcessTrackers() {
        Flux<ReportProcessTracker> reportProcessTrackerFlux = springReactiveWebFluxProcessTrackerRepository.findAll();
        Flux<ReportProcessTrackerDTO> reportProcessTrackerDTOFlux = reportProcessTrackerFlux
                .flatMap(reportProcessTracker -> convertToReportProcessTrackerDTO(reportProcessTracker));
        return reportProcessTrackerDTOFlux;
    }

    private Mono<ReportProcessTrackerDTO> convertToReportProcessTrackerDTO(ReportProcessTracker processTracker) {
        ReportProcessTrackerDTO reportProcessTrackerDTO = new ReportProcessTrackerDTO();
        reportProcessTrackerDTO.setId(processTracker.getId());
        reportProcessTrackerDTO.setTraceId(processTracker.getTraceId());
        reportProcessTrackerDTO.setServiceName(processTracker.getServiceName());
        reportProcessTrackerDTO.setProcessName(processTracker.getProcessName());
        reportProcessTrackerDTO.setPath(processTracker.getPath());
        reportProcessTrackerDTO.setCreatedBy(processTracker.getCreatedBy());
        reportProcessTrackerDTO.setCreatedTime(processTracker.getCreatedTimestamp());
        return Mono.just(reportProcessTrackerDTO);
    }

    @Override
    public Mono<ReportProcessTrackerDTO> findByReportProcessTrackerTraceId(String traceId) {
        Mono<ReportProcessTracker> reportProcessTrackerMono = springReactiveWebFluxProcessTrackerRepository.findByTraceId(traceId);
        return reportProcessTrackerMono.flatMap(reportProcessTracker -> convertToReportProcessTrackerDTO(reportProcessTracker));
    }

    @Override
    public Mono<ReportFileTracker> saveReportFileTracker(ReportFileTrackerDTO reportFileTrackerDTO) {
        ReportFileTracker reportFileTracker = new ReportFileTracker();
        reportFileTracker.setTraceId(UUID.randomUUID().toString());
        reportFileTracker.setReportProcessId(reportFileTrackerDTO.getReportProcessId());
        reportFileTracker.setFileName(reportFileTrackerDTO.getFileName());
        reportFileTracker.setFileType(reportFileTrackerDTO.getFileType());
        reportFileTracker.setCreatedBy(reportFileTrackerDTO.getCreatedBy());
        reportFileTracker.setCreatedTimestamp(LocalDateTime.now());
        return springReactiveWebFluxFileTrackerRepository.save(reportFileTracker);
    }

    @Override
    public Flux<ReportFileTrackerDTO> getAllReportFileTrackers() {
        Flux<ReportFileTracker> reportFileTrackerFlux = springReactiveWebFluxFileTrackerRepository.findAll();
        return reportFileTrackerFlux
                .flatMap(reportFileTracker -> convertToReportFileTrackerDTO(reportFileTracker));
    }

    private Mono<ReportFileTrackerDTO> convertToReportFileTrackerDTO(ReportFileTracker fileTracker) {
        ReportFileTrackerDTO reportFileTrackerDTO = new ReportFileTrackerDTO();
        reportFileTrackerDTO.setId(fileTracker.getId());
        reportFileTrackerDTO.setTraceId(fileTracker.getTraceId());
        reportFileTrackerDTO.setReportProcessId(fileTracker.getReportProcessId());
        reportFileTrackerDTO.setFileName(fileTracker.getFileName());
        reportFileTrackerDTO.setFileType(fileTracker.getFileType());
        reportFileTrackerDTO.setCreatedBy(fileTracker.getCreatedBy());
        reportFileTrackerDTO.setCreatedTime(fileTracker.getCreatedTimestamp());
        return Mono.just(reportFileTrackerDTO);
    }

    public Mono<ReportFileTrackerDTO> findByReportFileTrackerTraceId(String traceId) {
        Mono<ReportFileTracker> reportFileTrackerMono = springReactiveWebFluxFileTrackerRepository.findByTraceId(traceId);
        return reportFileTrackerMono.flatMap(reportFileTracker -> convertToReportFileTrackerDTO(reportFileTracker));
    }

    @Override
    public Flux<ReportFileTrackerDTO> findReportFileTrackersByProcessId(String reportProcessId) {
        Flux<ReportFileTracker> reportFileTrackerFlux = springReactiveWebFluxFileTrackerRepository.findByReportProcessId(reportProcessId);
        return reportFileTrackerFlux
                .flatMap(reportFileTracker -> convertToReportFileTrackerDTO(reportFileTracker));
    }

    @Override
    public Mono<ReportProcessFileTrackerDTO> findReportProcessAndFileTrackerByProcessId(String reportProcessId) {
        Flux<ReportFileTracker> reportFileTrackerFlux = springReactiveWebFluxFileTrackerRepository.findByReportProcessId(reportProcessId);
        Flux<ReportFileTrackerDTO> reportFileTrackerDTOFlux = reportFileTrackerFlux
                .flatMap(reportFileTracker -> convertToReportFileTrackerDTO(reportFileTracker));
        Mono<ReportProcessTracker> reportProcessTrackerMono = springReactiveWebFluxProcessTrackerRepository.findByTraceId(reportProcessId);
        Mono<ReportProcessTrackerDTO> reportProcessTrackerDTOMono = reportProcessTrackerMono
                .flatMap(reportProcessTracker -> convertToReportProcessTrackerDTO(reportProcessTracker));
        return reportProcessTrackerDTOMono.zipWith(
                reportFileTrackerDTOFlux.collectList(),
                (reportProcessTrackerDTO, reportFileTrackerDTO) -> new ReportProcessFileTrackerDTO(
                        reportProcessTrackerDTO.getId(),
                        reportProcessTrackerDTO.getTraceId(),
                        reportProcessTrackerDTO.getServiceName(),
                        reportProcessTrackerDTO.getProcessName(),
                        reportProcessTrackerDTO.getPath(),
                        reportProcessTrackerDTO.getCreatedBy(),
                        reportProcessTrackerDTO.getCreatedTime(),
                        reportFileTrackerDTO
                )
        );
    }

    @Override
    public Flux<ReportProcessFileTrackerDTO> getHistoricalRecords() {
        return getAllReportProcessTrackers()
                .flatMapSequential(reportProcessTrackerDTO ->
                        findReportProcessAndFileTrackerByProcessId(reportProcessTrackerDTO.getTraceId()));
    }

    @Override
    public Mono<byte[]> generatePDFForReportProcessFileTracker() {
        return getHistoricalRecords().collectList()
                .map(reportProcessFileTrackerDTO -> reportProcessFilePDFGenerator.createPDF(reportProcessFileTrackerDTO));
    }


}
