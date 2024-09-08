package com.springcavaj.reactive.repository;

import com.springcavaj.reactive.entity.ReportProcessTracker;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface SpringReactiveWebFluxProcessTrackerRepository extends ReactiveCrudRepository<ReportProcessTracker, BigInteger> {


}
