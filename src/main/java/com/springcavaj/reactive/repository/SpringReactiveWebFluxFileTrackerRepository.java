package com.springcavaj.reactive.repository;

import com.springcavaj.reactive.entity.ReportFileTracker;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public interface SpringReactiveWebFluxFileTrackerRepository extends ReactiveCrudRepository<ReportFileTracker, BigInteger> {

    Mono<ReportFileTracker> findByTraceId(String traceId);
    Flux<ReportFileTracker> findByReportProcessId(String reportProcessId);
    Mono<Void> deleteAllByReportProcessId(String reportProcessId);
}
