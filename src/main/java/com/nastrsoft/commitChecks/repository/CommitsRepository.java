package com.nastrsoft.commitChecks.repository;

import com.nastrsoft.commitChecks.entity.CommitsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface CommitsRepository extends CrudRepository<CommitsEntity, Long> {
//    Optional<CommitsEntity> findById(Long id);

    /*default List<CommitsEntity> findByJiraVersion(String jira_version) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(f -> StringUtils.equalsIgnoreCase(f.getRelease(), jira_version))
                .collect(Collectors.toList());
    }*/
    /*default List<CommitsEntity> findByReleaseId(Long releaseId) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(f -> f.getReleaseId() == releaseId)
                .collect(Collectors.toList());
    }*/
    @Query("from CommitsEntity r where r.releaseId = :releaseId")
    Iterable<CommitsEntity> findAllByReleaseId(@Param("releaseId") Long releaseId);

    default List<CommitsEntity> findByReleaseId(Long releaseId) {
        return StreamSupport.stream(findAllByReleaseId(releaseId).spliterator(), false)
                .collect(Collectors.toList());
    }
    /*@Query("from CommitsEntity r where r.jiraVersion = :jiraVersion")
    Iterable<CommitsEntity> findAllByJiraVersion(@Param("jiraVersion") String jiraVersion);

    default List<CommitsEntity> findByJiraVersion(String jiraVersion) {
        return StreamSupport.stream(findAllByJiraVersion(jiraVersion).spliterator(), false)
                .collect(Collectors.toList());
    }*/
}