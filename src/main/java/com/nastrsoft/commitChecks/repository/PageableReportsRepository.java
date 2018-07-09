package com.nastrsoft.commitChecks.repository;

import com.nastrsoft.commitChecks.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageableReportsRepository extends PagingAndSortingRepository<ReportEntity, Long> {

    Page<ReportEntity> findById(Long id, Pageable page);
}
