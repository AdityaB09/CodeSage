package com.codesage.repository;

import com.codesage.model.AnalysisRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRecordRepository extends JpaRepository<AnalysisRecord, Long> {
}
