package com.springcavaj.reactive.router;


import com.springcavaj.reactive.handler.SpringReactiveWebFluxHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class SpringReactiveWebFluxRouter {
    private static Logger LOG = LoggerFactory.getLogger(SpringReactiveWebFluxRouter.class);

    @Bean
    public RouterFunction<ServerResponse> processTrackerRoutes(SpringReactiveWebFluxHandler springReactiveWebFluxHandler) {
        return route(POST("/flux/processTrackers"), springReactiveWebFluxHandler::createReportProcessTracker)
                .andRoute(GET("/flux/processTrackers"), springReactiveWebFluxHandler::getAllReportProcessTrackers)
                .andRoute(POST("/flux/fileTrackers"), springReactiveWebFluxHandler::createReportFileTracker)
                .andRoute(GET("/flux/fileTrackers"), springReactiveWebFluxHandler::getAllReportFileTrackers)
                .andRoute(GET("/flux/processTracker/{traceId}"), springReactiveWebFluxHandler::getReportProcessTrackerByTraceId)
                .andRoute(GET("/flux/fileTracker/{traceId}"), springReactiveWebFluxHandler::getReportFileTrackerByTraceId)
                .andRoute(GET("/flux/fileTrackers/{reportProcessId}"), springReactiveWebFluxHandler::getReportFileTrackersByProcessId)
                .andRoute(GET("/flux/processFileTrackers/{traceId}"), springReactiveWebFluxHandler::getReportProcessFileTrackersByReportTraceId)
                .andRoute(GET("/flux/history"), springReactiveWebFluxHandler::getHistoricalRecords)
                .andRoute(GET("/flux/generatePDF"), springReactiveWebFluxHandler::getReportProcessAndFileTrackersInPDF)
                .andRoute(PUT("/flux/updateProcessTracker/{traceId}"), springReactiveWebFluxHandler::updateReportProcessTracker)
                .andRoute(PUT("/flux/updateFileTracker/{traceId}"), springReactiveWebFluxHandler::updateReportFileTracker)
                .andRoute(DELETE("/flux/deleteFileTracker/{id}"), springReactiveWebFluxHandler::deleteReportFileTrackerByID)
                .andRoute(DELETE("/flux/deleteProcessTracker/{id}"), springReactiveWebFluxHandler::deleteReportProcessTrackerByID);
    }

}
