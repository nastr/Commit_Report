package com.nastrsoft.commitChecks.back_end;

import com.nastrsoft.commitChecks.entity.BranchesEntity;
import com.nastrsoft.commitChecks.entity.CommitsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.nastrsoft.commitChecks.back_end.Fields.*;

public class StashRepoImpl implements SourceRepo {
    private static final Logger logger = LoggerFactory.getLogger(StashRepoImpl.class);
    private Map<Fields, String> credentials = null;
    private Set<String> repos = null;

    private StashRepoImpl() {
    }

    public StashRepoImpl(Map<Fields, String> credentials, Set<String> repos) {
        this.credentials = credentials;
        this.repos = repos;
    }

    @Override
    public List<CommitsEntity> getCommitsEtity() {
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Stream.of(
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev1", "author", localDate, "message", "", "repo1", "key-789", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev2", "author", localDate, "message", "", "repo1", "key-901", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev3", "author", localDate, "message", "", "repo1", "key-230", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev4", "author", localDate, "message", "", "repo1", "key-560", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev5", "author", localDate, "message", "", "repo1", "key-234", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev6", "author", localDate, "message", "", "repo1", "key-456", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev7", "author", localDate, "message", "", "repo1", "Error", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev9", "author", localDate, "message", "", "repo1", "Error", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev8", "author", localDate, "message", "", "repo1", "key-4814", credentials.get(jiraVersion), "release/branch")
        ).collect(Collectors.toList());
    }

    @Override
    public List<BranchesEntity> getBranchesEntity() {
        return Stream.of(
                new BranchesEntity(Long.valueOf(credentials.get(releaseId)), "repo_name1", "branch_name1", "TBL", "CBL"),
                new BranchesEntity(Long.valueOf(credentials.get(releaseId)), "repo_name1", "branch_name3", "TBL", "CBL"),
                new BranchesEntity(Long.valueOf(credentials.get(releaseId)), "repo_name1", "branch_name4", "TBL", "CBL")
        ).collect(Collectors.toList());
    }

    @Override
    public Set<String> getData(Set<String> repos) {
        return null;
    }

    @Override
    public Set<String> getData() {
        return Stream.of("jira-123", "jira-456", "jira-789").collect(Collectors.toSet());
    }

    private class CollectLogs implements Runnable {

        @Override
        public void run() {

        }
    }
}
