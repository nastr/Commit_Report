package com.nastrsoft.commitChecks.repository;

import com.nastrsoft.commitChecks.entity.CorrectionsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface CorrectionsRepository extends CrudRepository<CorrectionsEntity, Long> {
    @Query("from CorrectionsEntity r where r.releaseId = :releaseId")
    Iterable<CorrectionsEntity> findAllByReleaseId(@Param("releaseId") Long releaseId);

    default List<CorrectionsEntity> findByReleaseId(Long releaseId) {
        return StreamSupport.stream(findAllByReleaseId(releaseId).spliterator(), false)
                .collect(Collectors.toList());
    }

    /*@Query("from CorrectionsEntity r where r.jiraVersion = :jiraVersion")
    Iterable<CorrectionsEntity> findAllByJiraVersion(@Param("jiraVersion") String jiraVersion);

    default List<CorrectionsEntity> findByJiraVersion(String jiraVersion) {
        return StreamSupport.stream(findAllByJiraVersion(jiraVersion).spliterator(), false)
                .collect(Collectors.toList());
    }*/
}
