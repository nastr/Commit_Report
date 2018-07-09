package com.nastrsoft.commitChecks.repository;

import com.nastrsoft.commitChecks.entity.BranchesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface BranchesRepository extends CrudRepository<BranchesEntity, Long> {

    /*default List<BranchesEntity> findByJiraVersion(String jira_version) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(f -> StringUtils.equalsIgnoreCase(f.getJira_version(), jira_version))
                .collect(Collectors.toList());
    }*/
    /*default List<BranchesEntity> findByReleaseId(Long releaseId) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(f -> f.getReleaseId() == releaseId)
                .collect(Collectors.toList());
    }*/

    @Query("from BranchesEntity r where r.releaseId = :releaseId")
    Iterable<BranchesEntity> findAllByReleaseId(@Param("releaseId") Long releaseId);

    default List<BranchesEntity> findByReleaseId(Long releaseId) {
        return StreamSupport.stream(findAllByReleaseId(releaseId).spliterator(), false)
                .collect(Collectors.toList());
    }

    /*@Query("from BranchesEntity r where r.jiraVersion = :jiraVersion")
    Iterable<BranchesEntity> findAllByJiraVersion(@Param("jiraVersion") String jiraVersion);

    default List<BranchesEntity> findByJiraVersion(String jiraVersion) {
        return StreamSupport.stream(findAllByJiraVersion(jiraVersion).spliterator(), false)
                .collect(Collectors.toList());
    }*/

}
