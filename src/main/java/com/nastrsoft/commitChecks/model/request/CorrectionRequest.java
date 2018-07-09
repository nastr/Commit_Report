package com.nastrsoft.commitChecks.model.request;

public class CorrectionRequest {
    private String repositoryName;
    private String commitsRevision;
    private String justification;
    private String justificationAuthor;
    private String reportJiraVersion;

    public CorrectionRequest() {
    }

    public CorrectionRequest(String repositoryName, String commitsRevision, String justification, String justificationAuthor, String reportJiraVersion) {
        this.repositoryName = repositoryName;
        this.commitsRevision = commitsRevision;
        this.justification = justification;
        this.justificationAuthor = justificationAuthor;
        this.reportJiraVersion = reportJiraVersion;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getCommitsRevision() {
        return commitsRevision;
    }

    public void setCommitsRevision(String commitsRevision) {
        this.commitsRevision = commitsRevision;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getJustificationAuthor() {
        return justificationAuthor;
    }

    public void setJustificationAuthor(String justificationAuthor) {
        this.justificationAuthor = justificationAuthor;
    }

    public String getReportJiraVersion() {
        return reportJiraVersion;
    }

    public void setReportJiraVersion(String reportJiraVersion) {
        this.reportJiraVersion = reportJiraVersion;
    }

    @Override
    public String toString() {
        return "CorrectionRequest{" +
                ", repositoryName='" + repositoryName + '\'' +
                ", commitsRevision='" + commitsRevision + '\'' +
                ", justification='" + justification + '\'' +
                ", justificationAuthor='" + justificationAuthor + '\'' +
                ", reportJiraVersion='" + reportJiraVersion + '\'' +
                '}';
    }
}
