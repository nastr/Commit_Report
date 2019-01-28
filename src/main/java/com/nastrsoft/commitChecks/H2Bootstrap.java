package com.nastrsoft.commitChecks;

import com.nastrsoft.commitChecks.entity.*;
import com.nastrsoft.commitChecks.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class H2Bootstrap implements CommandLineRunner {

    @Autowired
    private ReportsRepository reportsRepository;

    @Autowired
    private CommitsRepository commitsRepository;

    @Autowired
    private JirasRepository jirasRepository;

    @Autowired
    private BranchesRepository branchesRepository;

    @Autowired
    private CorrectionsRepository correctionsRepository;

    @Autowired
    CredentialsRepository credentialsRepository;

    @Override
    public void run(String... args) {
        String jiraVersion = "18-02";
//        System.out.println("Bootstrapping data: ");
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<ReportEntity> releasesEntities = Stream.of(
                new ReportEntity("18.6", "DRAFT", "", localDate),
                new ReportEntity("18.5", "DRAFT", "", localDate),
                new ReportEntity(jiraVersion, "complete", "test1", localDate)
        ).collect(Collectors.toList());
        reportsRepository.saveAll(releasesEntities);

        StreamSupport.stream(reportsRepository.findAll().spliterator(), false).forEach(ReportEntity ->
                System.out.println(ReportEntity.getJira_version()));


        Long releaseId = reportsRepository.findByJiraVersion(jiraVersion).getId();

        List<CommitsEntity> commitsEntities = Stream.of(
                new CommitsEntity(releaseId, "rev1", "author", localDate, "message", "", "repo1", "key-789", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev2", "author", localDate, "message", "", "repo1", "key-901", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev3", "author", localDate, "message", "", "repo1", "key-230", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev4", "author", localDate, "message", "", "repo1", "key-560", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev5", "author", localDate, "message", "", "repo1", "key-234", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev6", "author", localDate, "message", "", "repo1", "key-456", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev7", "author", localDate, "message", "", "repo1", "Error", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev9", "author", localDate, "message", "", "repo1", "Error", jiraVersion, "release/branch"),
                new CommitsEntity(releaseId, "rev8", "author", localDate, "message", "", "repo1", "key-4814", jiraVersion, "release/branch")
        ).collect(Collectors.toList());

        List<JirasEntity> jirasEntities = Stream.of(
                new JirasEntity(releaseId, "key-678", "title", "defect", "in progress", "user name",
                        "reporter Name", "", "key-789", "18-02", "fix", "sprint"),
                new JirasEntity(releaseId, "key-789", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-678", "", "", "fix", "sprint"),
                new JirasEntity(releaseId, "key-890", "title", "story", "in progress", "user name",
                        "reporter Name", "", "key-901", "affected", "18-02", "sprint"),
                new JirasEntity(releaseId, "key-901", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-890", "", "affected", "", "sprint"),

                new JirasEntity(releaseId, "key-120", "title", "defect", "in progress", "user name",
                        "reporter Name", "", "key-230", "17-12", "fix", "sprint"),
                new JirasEntity(releaseId, "key-230", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-120", "", "", "fix", "sprint"),
                new JirasEntity(releaseId, "key-340", "title", "story", "in progress", "user name",
                        "reporter Name", "", "key-560", "affected", "17-12", "sprint"),
                new JirasEntity(releaseId, "key-560", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-340", "", "affected", "", "sprint"),

                new JirasEntity(releaseId, "key-123", "title", "defect", "in progress", "user name",
                        "reporter Name", "", "key-234", "17-11", "fix", "sprint"),
                new JirasEntity(releaseId, "key-234", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-123", "", "", "fix", "sprint"),
                new JirasEntity(releaseId, "key-345", "title", "story", "in progress", "user name",
                        "reporter Name", "", "key-456", "affected", "17-11", "sprint"),
                new JirasEntity(releaseId, "key-456", "title", "sub-task", "in progress", "user name",
                        "reporter Name", "key-345", "", "affected", "", "sprint"),

                new JirasEntity(releaseId, "Error", "Undefined JIRA", "", "", "", "", "", "", "", "", ""),
                new JirasEntity(releaseId, "key-4814", "Undefined JIRA", "", "", "", "", "", "", "", "", "")
        ).collect(Collectors.toList());

        List<CorrectionsEntity> correctionsEntities = Stream.of(
                new CorrectionsEntity(releaseId, "repo1", "rev5", "justification1", "Autor", localDate),
                new CorrectionsEntity(releaseId, "repo1", "rev6", "justification2", "Autor", localDate)
        ).collect(Collectors.toList());
        commitsRepository.saveAll(commitsEntities);
        jirasRepository.saveAll(jirasEntities);
        correctionsRepository.saveAll(correctionsEntities);

        List<BranchesEntity> branchesEntities = Stream.of(
                new BranchesEntity(releaseId, "repo1", "path", "tbl", "cbl"),
                new BranchesEntity(releaseId, "repo2", "path", "tbl", "cbl"),
                new BranchesEntity(releaseId, "repo3", "path", "tbl", "cbl")
        ).collect(Collectors.toList());
        branchesRepository.saveAll(branchesEntities);

        List<CredentialsEntity> credentialsEntities = Stream.of(
                new CredentialsEntity("git.username", ""),
                new CredentialsEntity("git.password", ""),
                new CredentialsEntity("git.url", ""),
                new CredentialsEntity("svn.username", ""),
                new CredentialsEntity("svn.password", ""),
                new CredentialsEntity("svn.url", ""),
                new CredentialsEntity("jira.username", ""),
                new CredentialsEntity("jira.password", ""),
                new CredentialsEntity("jira.url", ""),
                new CredentialsEntity("jira.projects", ""),
                new CredentialsEntity("proxy", "host:port")
        ).collect(Collectors.toList());
        credentialsRepository.saveAll(credentialsEntities);
    }

}
