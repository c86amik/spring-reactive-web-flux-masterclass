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
        return route(POST("flux/processTrackers"), springReactiveWebFluxHandler::createReportProcessTracker)
                .andRoute(GET("/flux/processTrackers"), springReactiveWebFluxHandler::getAllReportProcessTrackers);
    }

}
