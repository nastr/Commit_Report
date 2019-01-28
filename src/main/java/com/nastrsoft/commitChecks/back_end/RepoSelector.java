package com.nastrsoft.commitChecks.back_end;

import com.nastrsoft.commitChecks.repository.CredentialsRepository;
import com.nastrsoft.commitChecks.repository.ReportsRepository;
import com.nastrsoft.commitChecks.repository.VCSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.nastrsoft.commitChecks.back_end.Fields.*;

@Component
//@Scope(value = "prototype")
public class RepoSelector {
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private VCSRepository VCSRepository;

    @Autowired
    private ReportsRepository reportsRepository;

    /*private static RepoSelector instance;

    public static synchronized RepoSelector getInstance() {
        if (instance == null)
            instance = new RepoSelector();
        return instance;
    }*/

    private RepoSelector() {
    }

    public SourceRepo getRepo(RepoType repoType, Long releaseId) {
//        Long releaseId = releasesRepository.findIdByJiraVersion(jiraVersion);
//        String jiraVersion = reportsRepository.findJiraVersionById(releaseId);
        String jiraVersion = reportsRepository.findById(releaseId).get().getJira_version();
        switch (repoType) {
            case GIT:
                return new StashRepoImpl(new HashMap<Fields, String>() {{
                    put(Fields.jiraVersion, jiraVersion);
                    put(url, credentialsRepository.findByName("git.url"));
                    put(username, credentialsRepository.findByName("git.username"));
                    put(password, credentialsRepository.findByName("git.password"));
                    put(Fields.releaseId, releaseId.toString());
                }}, VCSRepository.findByName("git"));
            case SVN:
                return new SVNRepoImpl(new HashMap<Fields, String>() {{
                    put(Fields.jiraVersion, jiraVersion);
                    put(url, credentialsRepository.findByName("svn.url"));
                    put(username, credentialsRepository.findByName("svn.username"));
                    put(password, credentialsRepository.findByName("svn.password"));
                    put(jiraProjects, credentialsRepository.findByName("jira.projects"));
                    put(Fields.releaseId, releaseId.toString());
                }}, VCSRepository.findByName("svn"));
            default:
                return null;
        }
    }
}
