package com.nastrsoft.commitChecks.repository;

import com.nastrsoft.commitChecks.entity.JirasEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface JirasRepository extends CrudRepository<JirasEntity, Long> {
    /*default List<JirasEntity> findByReleaseId(Long releaseId) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(f -> f.getReleaseId() == releaseId)
                .collect(Collectors.toList());
    }*/

    @Query("from JirasEntity r where r.releaseId = :releaseId")
    Iterable<JirasEntity> findAllByReleaseId(@Param("releaseId") Long releaseId);

    default List<JirasEntity> findByReleaseId(Long releaseId) {
        return StreamSupport.stream(findAllByReleaseId(releaseId).spliterator(), false)
                .collect(Collectors.toList());
    }
    /*@Query("from JirasEntity r where r.jiraVersion = :jiraVersion")
    Iterable<JirasEntity> findAllByJiraVersion(@Param("jiraVersion") String jiraVersion);

    default List<JirasEntity> findByJiraVersion(String jiraVersion) {
        return StreamSupport.stream(findAllByJiraVersion(jiraVersion).spliterator(), false)
                .collect(Collectors.toList());
    }*/
}
