package com.springcavaj.reactive.repository;

import com.springcavaj.reactive.entity.ReportFileTracker;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface SpringReactiveWebFluxFileTrackerRepository extends ReactiveCrudRepository<ReportFileTracker, BigInteger> {
}
