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

import static com.nastrsoft.commitChecks.back_end.Fields.jiraVersion;
import static com.nastrsoft.commitChecks.back_end.Fields.releaseId;

public class SVNRepoImpl implements SourceRepo {
    private static final Logger logger = LoggerFactory.getLogger(SVNRepoImpl.class);
    private Map<Fields, String> credentials = null;
    private Set<String> repos = null;

    private SVNRepoImpl() {
    }

    public SVNRepoImpl(Map<Fields, String> credentials, Set<String> repos) {
        this.credentials = credentials;
        this.repos = repos;
    }

    @Override
    public List<CommitsEntity> getCommitsEtity() {
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Stream.of(
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev10", "author", localDate, "message", "", "repo2", "key-789", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev20", "author", localDate, "message", "", "repo2", "key-901", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev30", "author", localDate, "message", "", "repo2", "key-230", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev40", "author", localDate, "message", "", "repo2", "key-560", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev50", "author", localDate, "message", "", "repo2", "key-234", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev60", "author", localDate, "message", "", "repo2", "key-456", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev70", "author", localDate, "message", "", "repo2", "Error", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev90", "author", localDate, "message", "", "repo2", "Error", credentials.get(jiraVersion), "release/branch"),
                new CommitsEntity(Long.valueOf(credentials.get(releaseId)), "rev80", "author", localDate, "message", "", "repo2", "key-4814", credentials.get(jiraVersion), "release/branch")
        ).collect(Collectors.toList());
    }

    @Override
    public List<BranchesEntity> getBranchesEntity() {
        return Stream.of(
                new BranchesEntity(Long.valueOf(credentials.get(releaseId)), "repo_name2", "branch_name5", "TBL", "CBL"),
                new BranchesEntity(Long.valueOf(credentials.get(releaseId)), "repo_name2", "branch_name6", "TBL", "CBL"),
                new BranchesEntity(Long.valueOf(credentials.get(releaseId)), "repo_name2", "branch_name7", "TBL", "CBL")
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
