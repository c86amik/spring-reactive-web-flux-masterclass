package com.springcavaj.reactive.handler;

import com.springcavaj.reactive.dto.ReportProcessTrackerDTO;
import com.springcavaj.reactive.entity.ReportProcessTracker;
import com.springcavaj.reactive.service.SpringReactiveWebFluxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
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
}
