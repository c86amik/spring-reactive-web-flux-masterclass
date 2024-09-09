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
}
