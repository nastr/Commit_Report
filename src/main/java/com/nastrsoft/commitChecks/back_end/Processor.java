package com.nastrsoft.commitChecks.back_end;


import com.nastrsoft.commitChecks.entity.BranchesEntity;
import com.nastrsoft.commitChecks.entity.CommitsEntity;
import com.nastrsoft.commitChecks.entity.JirasEntity;
import com.nastrsoft.commitChecks.entity.ReportEntity;
import com.nastrsoft.commitChecks.model.response.ActionResponse;
import com.nastrsoft.commitChecks.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.nastrsoft.commitChecks.back_end.Fields.*;

@Component
@Scope(value = "singleton")
public class Processor {

    private static final Logger logger = LoggerFactory.getLogger(Processor.class);

    @Autowired
    private CommitsRepository commitsRepository;

    @Autowired
    private BranchesRepository branchesRepository;

    @Autowired
    private JirasRepository jirasRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private ReportsRepository reportsRepository;

    @Autowired
    private static ReportsRepository reportsRepositoryStatic;

    @Autowired
    RepoSelector repoSelector;

    private static Map<Long, Processor> store = new HashMap<>();
    private String status;

    private Processor() {
    }

    /*public static final class Processor {
        private Processor(String parameter) {
            // init
        }

        public void getData() {
            // some useful operation
        }
    }*/

    public static Processor getInstance(Long releaseId) {
        synchronized (store) {
            if (store.containsKey(releaseId))
                return store.get(releaseId);
            else {
                Processor processor = new Processor();
                store.put(releaseId, processor);
                return processor;
            }
        }
    }

    public static Processor getInstance(String jiraVersion) {
        Long releaseId = reportsRepositoryStatic.findIdByJiraVersion(jiraVersion);
        return getInstance(releaseId);
    }

    public String getStatus() {
        return status;
    }

    public ResponseEntity<ActionResponse> getData(Long releaseId) {
        logger.info("getData\t" + releaseId);
        ActionResponse response;
        ReportEntity reportEntity = reportsRepository.findById(releaseId).get();
        if (StringUtils.isEmpty(status)) {
            status = "IN PROGRESS";
            reportEntity.setStatus(this.status);
            reportsRepository.save(reportEntity);
//            reportsRepository.updateStatusByRleaseId(status, releaseId);
        } else {
            response = new ActionResponse(releaseId.toString(), this.getStatus());
            return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
        }
        /*String currentStatus = releasesRepository.findByJiraVersion(jiraVersion).getStatus();
        if (StringUtils.equalsIgnoreCase(currentStatus, status)) {
            response = new ActionResponse(jiraVersion, status);
            return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
        }*/

        SourceRepo git = repoSelector.getRepo(RepoType.GIT, releaseId);
        SourceRepo svn = repoSelector.getRepo(RepoType.SVN, releaseId);

        /*SourceRepo svn = new SVNRepoImpl(new HashMap<Fields, String>() {{
            put(Fields.jiraVersion, jiraVersion);
            put(url, credentialsRepository.findByName("svn.url"));
            put(username, credentialsRepository.findByName("svn.username"));
            put(password, credentialsRepository.findByName("svn.password"));
            put(jiraProjects, credentialsRepository.findByName("jira.projects"));
            put(releaseId, releaseId.toString());
        }}, reposRepository.findByName("svn"));
        SourceRepo git = new StashRepoImpl(new HashMap<Fields, String>() {{
            put(Fields.jiraVersion, jiraVersion);
            put(url, credentialsRepository.findByName("git.url"));
            put(username, credentialsRepository.findByName("git.username"));
            put(password, credentialsRepository.findByName("git.password"));
            put(Fields.releaseId, releaseId.toString());
        }}, reposRepository.findByName("git"));*/
        Jira jira = new Jira(new HashMap<Fields, String>() {{
            put(url, credentialsRepository.findByName("jira.url"));
            put(username, credentialsRepository.findByName("jira.username"));
            put(password, credentialsRepository.findByName("jira.password"));
            put(Fields.releaseId, releaseId.toString());
        }});
        Set<String> jiraKeys = Stream.of(svn, git)
                .filter(Objects::nonNull)
                .parallel()
                .map(SourceRepo::getData)
                .flatMap(m -> m.stream())
                .collect(Collectors.toSet());
        List<CommitsEntity> commitsEntities = Stream.of(svn, git)
                .filter(Objects::nonNull)
                .map(SourceRepo::getCommitsEtity)
                .flatMap(m -> m.stream())
                .collect(Collectors.toList());
        List<BranchesEntity> branchesEntities = Stream.of(svn, git)
                .filter(Objects::nonNull)
                .map(SourceRepo::getBranchesEntity)
                .flatMap(m -> m.stream())
                .collect(Collectors.toList());
        if (jiraKeys == null || jiraKeys.size() == 0) {
            logger.error("jiraKeys empty");
            return null;
        }
        logger.info("jiraKeys\t" + jiraKeys.size());
        logger.info("commitsEntities\t" + commitsEntities.size());
        logger.info("branchesEntities\t" + branchesEntities.size());

        List<JirasEntity> jirasEntities = jira.getData(jiraKeys);
        if (jirasEntities == null || jirasEntities.size() == 0) {
            logger.error("jirasEntities empty");
            return null;
        }
        logger.info("jirasEntities\t" + jirasEntities.size());

        commitsRepository.saveAll(commitsEntities);
        branchesRepository.saveAll(branchesEntities);
        jirasRepository.saveAll(jirasEntities);
//        List<CorrectionsEntity> correctionsEntities = correctionsRepository.findByJiraVersion(jiraVersion);
        status = "COMPLETE";
        reportEntity.setStatus(this.status);
        reportsRepository.save(reportEntity);
        logger.info("All envies obtained");
        response = new ActionResponse(releaseId.toString(), this.getStatus());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
