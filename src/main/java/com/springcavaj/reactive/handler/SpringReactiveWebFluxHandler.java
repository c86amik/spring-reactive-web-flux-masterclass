package com.springcavaj.reactive.handler;

import com.springcavaj.reactive.dto.ReportFileTrackerDTO;
import com.springcavaj.reactive.dto.ReportProcessFileTrackerDTO;
import com.springcavaj.reactive.dto.ReportProcessTrackerDTO;
import com.springcavaj.reactive.service.SpringReactiveWebFluxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class SpringReactiveWebFluxHandler {

    private  static Logger LOG = LoggerFactory.getLogger(SpringReactiveWebFluxHandler.class);

    @Autowired
    private SpringReactiveWebFluxService springReactiveWebFluxService;

    public Mono<ServerResponse> createReportProcessTracker(ServerRequest serverRequest) {
        LOG.info("SpringReactiveWebFluxHandler -> createReportProcessTracker() operation started");
        return serverRequest.bodyToMono(ReportProcessTrackerDTO.class)
                .flatMap(reportProcessTrackerDTO -> springReactiveWebFluxService.saveReportProcessTracker(reportProcessTrackerDTO))
                .flatMap(savedReportProcessTracker -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(savedReportProcessTracker)));
    }

    public Mono<ServerResponse> getAllReportProcessTrackers(ServerRequest serverRequest) {
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(springReactiveWebFluxService.getAllReportProcessTrackers(), ReportProcessTrackerDTO.class);
    }

    public Mono<ServerResponse> createReportFileTracker(ServerRequest serverRequest) {
        LOG.info("SpringReactiveWebFluxHandler -> createReportFileTracker() operation started");
        return serverRequest.bodyToMono(ReportFileTrackerDTO.class)
                .flatMap(reportFileTrackerDTO -> springReactiveWebFluxService.saveReportFileTracker(reportFileTrackerDTO))
                .flatMap(savedReportFileTracker -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(savedReportFileTracker)));
    }

    public Mono<ServerResponse> getAllReportFileTrackers(ServerRequest serverRequest) {
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(springReactiveWebFluxService.getAllReportFileTrackers(), ReportFileTrackerDTO.class);
    }

    public Mono<ServerResponse> getReportProcessTrackerByTraceId(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(springReactiveWebFluxService.findByReportProcessTrackerTraceId(serverRequest.pathVariable("traceId")), ReportProcessTrackerDTO.class);
    }

    public Mono<ServerResponse> getReportFileTrackerByTraceId(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(springReactiveWebFluxService.findByReportFileTrackerTraceId(serverRequest.pathVariable("traceId")), ReportFileTrackerDTO.class);
    }

    public Mono<ServerResponse> getReportFileTrackersByProcessId(ServerRequest serverRequest) {
        Flux<ReportFileTrackerDTO> reportFileTrackerDTOFlux = springReactiveWebFluxService
                .findReportFileTrackersByProcessId(serverRequest.pathVariable("reportProcessId"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(reportFileTrackerDTOFlux, ReportFileTrackerDTO.class);
    }

    public Mono<ServerResponse> getReportProcessFileTrackersByReportTraceId(ServerRequest serverRequest) {
        return springReactiveWebFluxService.findReportProcessAndFileTrackerByProcessId(serverRequest.pathVariable("traceId"))
                .flatMap(reportProcessFileTrackerDTO -> ServerResponse.ok().bodyValue(reportProcessFileTrackerDTO))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getHistoricalRecords(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(springReactiveWebFluxService.getHistoricalRecords(), ReportProcessFileTrackerDTO.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getReportProcessAndFileTrackersInPDF(ServerRequest serverRequest) {
        return springReactiveWebFluxService.generatePDFForReportProcessFileTracker()
                .flatMap(pdfBytes -> ServerResponse.ok()
                        .header("Content-Disposition", "attachment; filename=Process_File_Tracker_Summary_Report.pdf")
                        .header("Content-Type", "application/pdf")
                        .bodyValue(pdfBytes))
                .onErrorResume(exception -> ServerResponse.status(500).bodyValue("Error Generating PDF: " + exception.getMessage()));
    }

    public Mono<ServerResponse> updateReportProcessTracker(ServerRequest serverRequest) {
        LOG.info("SpringReactiveWebFluxHandler -> updateReportProcessTracker() operation started");
        String traceId = serverRequest.pathVariable("traceId");
        LOG.info("SpringReactiveWebFluxHandler -> updateReportProcessTracker() traceId : {}", traceId);
        return serverRequest.bodyToMono(ReportProcessTrackerDTO.class)
                .flatMap(reportProcessTrackerDTOOriginal -> springReactiveWebFluxService.findByReportProcessTrackerTraceId(traceId)
                    .flatMap(reportProcessTrackerDTO -> {
                        reportProcessTrackerDTO.setId(reportProcessTrackerDTOOriginal.getId());
                        reportProcessTrackerDTO.setTraceId(reportProcessTrackerDTOOriginal.getTraceId());
                        reportProcessTrackerDTO.setProcessName(reportProcessTrackerDTOOriginal.getProcessName());
                        reportProcessTrackerDTO.setPath(reportProcessTrackerDTOOriginal.getPath());
                        reportProcessTrackerDTO.setServiceName(reportProcessTrackerDTOOriginal.getServiceName());
                        reportProcessTrackerDTO.setCreatedBy(reportProcessTrackerDTOOriginal.getCreatedBy());
                        reportProcessTrackerDTO.setCreatedTime(reportProcessTrackerDTOOriginal.getCreatedTime());
                        return springReactiveWebFluxService.saveReportProcessTracker(reportProcessTrackerDTO);
                    })
                ).flatMap(savedReportProcessTracker -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(fromValue(savedReportProcessTracker)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateReportFileTracker(ServerRequest serverRequest) {
        LOG.info("SpringReactiveWebFluxHandler -> updateReportFileTracker() operation started");
        String traceId = serverRequest.pathVariable("traceId");
        LOG.info("SpringReactiveWebFluxHandler -> updateReportFileTracker() traceId : {}", traceId);
        return serverRequest.bodyToMono(ReportFileTrackerDTO.class)
                .flatMap(reportFileTrackerDTOOriginal -> springReactiveWebFluxService.findByReportFileTrackerTraceId(traceId)
                        .flatMap(reportFileTrackerDTO -> {
                            reportFileTrackerDTO.setId(reportFileTrackerDTOOriginal.getId());
                            reportFileTrackerDTO.setTraceId(reportFileTrackerDTOOriginal.getTraceId());
                            reportFileTrackerDTO.setReportProcessId(reportFileTrackerDTOOriginal.getReportProcessId());
                            reportFileTrackerDTO.setFileType(reportFileTrackerDTOOriginal.getFileType());
                            reportFileTrackerDTO.setFileName(reportFileTrackerDTOOriginal.getFileName());
                            reportFileTrackerDTO.setCreatedBy(reportFileTrackerDTOOriginal.getCreatedBy());
                            reportFileTrackerDTO.setCreatedTime(reportFileTrackerDTOOriginal.getCreatedTime());
                            return springReactiveWebFluxService.saveReportFileTracker(reportFileTrackerDTO);
                        })
                ).flatMap(savedReportFileTracker -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(savedReportFileTracker)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteReportFileTrackerByID(ServerRequest serverRequest) {
        BigInteger id = BigInteger.valueOf(Long.parseLong(serverRequest.pathVariable("id")));
        LOG.info("SpringReactiveWebFluxHandler -> deleteReportFileTrackerByID() -> ID : {}", id);
        return springReactiveWebFluxService.deleteReportFileTracker(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> deleteReportProcessTrackerByID(ServerRequest serverRequest) {
        BigInteger id = BigInteger.valueOf(Long.parseLong(serverRequest.pathVariable("id")));
        LOG.info("SpringReactiveWebFluxHandler -> deleteReportProcessTrackerByID -> ID : {}", id);
        return springReactiveWebFluxService.deleteReportProcessTracker(id)
                .then(ServerResponse.noContent().build());
    }
}
