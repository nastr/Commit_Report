package com.nastrsoft.commitChecks.model.response;

public class DashboardTableResponse {
    private String commits_revision;
    private String repository_name;
    private String commits_message;
    private String commits_authors;
    private String jira_artifact;
    private String jira_title;
    private String jira_parent_artifact;
    private String jira_type;
    private String scope;
    private String jira_version;

    public DashboardTableResponse() {
    }

    public DashboardTableResponse(String commits_revision, String repository_name, String commits_message, String commits_authors, String jira_artifact, String jira_title, String jira_parent_artifact, String jira_type, String scope, String jira_version) {
        this.commits_revision = commits_revision;
        this.repository_name = repository_name;
        this.commits_message = commits_message;
        this.commits_authors = commits_authors;
        this.jira_artifact = jira_artifact;
        this.jira_title = jira_title;
        this.jira_parent_artifact = jira_parent_artifact;
        this.jira_type = jira_type;
        this.scope = scope;
        this.jira_version = jira_version;
    }

    public String getCommits_revision() {
        return commits_revision;
    }

    public void setCommits_revision(String commits_revision) {
        this.commits_revision = commits_revision;
    }

    public String getRepository_name() {
        return repository_name;
    }

    public void setRepository_name(String repository_name) {
        this.repository_name = repository_name;
    }

    public String getCommits_message() {
        return commits_message;
    }

    public void setCommits_message(String commits_message) {
        this.commits_message = commits_message;
    }

    public String getCommits_authors() {
        return commits_authors;
    }

    public void setCommits_authors(String commits_authors) {
        this.commits_authors = commits_authors;
    }

    public String getJira_artifact() {
        return jira_artifact;
    }

    public void setJira_artifact(String jira_artifact) {
        this.jira_artifact = jira_artifact;
    }

    public String getJira_title() {
        return jira_title;
    }

    public void setJira_title(String jira_title) {
        this.jira_title = jira_title;
    }

    public String getJira_parent_artifact() {
        return jira_parent_artifact;
    }

    public void setJira_parent_artifact(String jira_parent_artifact) {
        this.jira_parent_artifact = jira_parent_artifact;
    }

    public String getJira_type() {
        return jira_type;
    }

    public void setJira_type(String jira_type) {
        this.jira_type = jira_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJira_version() {
        return jira_version;
    }

    public void setJira_version(String jira_version) {
        this.jira_version = jira_version;
    }
}
