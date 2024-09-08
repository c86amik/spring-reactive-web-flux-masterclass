package com.springcavaj.reactive.repository;

import com.springcavaj.reactive.entity.ReportProcessTracker;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringReactiveWebFluxRepository extends ReactiveCrudRepository<ReportProcessTracker, String> {


}
