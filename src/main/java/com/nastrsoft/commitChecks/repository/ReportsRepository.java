package com.nastrsoft.commitChecks.repository;

import com.nastrsoft.commitChecks.entity.ReportEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.StreamSupport;

@Repository
public interface ReportsRepository extends CrudRepository<ReportEntity, Long> { //

    @Modifying
    @Query("update ReportEntity r set r.status = :status where r.jira_version = :jiraVersion")
    void updateStatusByJiraVersion(@Param("status") String status, @Param("jiraVersion") String jiraVersion);

    @Modifying
    @Query("update ReportEntity u set u.status = :status where u.id = :releaseId")
    int updateStatusByRleaseId(@Param("status") String status,
                               @Param("releaseId") Long releaseId);

    @Modifying
    @Query(value = "update ReportEntity u set u.status = ? where u.releaseId = ?", nativeQuery = true)
    int updateStatusByRleaseIdNative(String status, Long releaseId);

    @Query("from ReportEntity r where r.jira_version LIKE CONCAT('%',:jiraVersion,'%')")
    Iterable<ReportEntity> findAllByJiraVersion(@Param("jiraVersion") String jiraVersion);

    default ReportEntity findByJiraVersion(String jiraVersion) {
        return StreamSupport.stream(findAllByJiraVersion(jiraVersion).spliterator(), false)
                .findFirst().get();
    }

    default Long findIdByJiraVersion(String jiraVersion) {
        return findByJiraVersion(jiraVersion).getId();
    }

    @Query("from ReportEntity r where r.id = :releaseId")
    Iterable<ReportEntity> findAllReleaseId(@Param("releaseId") Long releaseId);

    default ReportEntity findByReleaseId(Long releaseId) {
//        List<ReportEntity> r = StreamSupport.stream(findAllReleaseId(releaseId).spliterator(), false).collect(Collectors.toList());
//        System.out.println(r);
        return StreamSupport.stream(findAllReleaseId(releaseId).spliterator(), false)
                .findFirst().get();
    }

    default String findJiraVersionById(Long releaseId) {
        return findByReleaseId(releaseId).getJira_version();
    }
}
