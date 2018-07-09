package com.nastrsoft.commitChecks.model.response;

import java.time.LocalDate;

public class CorrectionsResponse {
    private String repositoryName;
    private String commitsRevision;
    private String justification;
    private String justificationAuthor;
    private LocalDate date;

    public CorrectionsResponse() {
    }

    public CorrectionsResponse(String repositoryName, String commitsRevision, String justification, String justificationAuthor, LocalDate date) {
        this.repositoryName = repositoryName;
        this.commitsRevision = commitsRevision;
        this.justification = justification;
        this.justificationAuthor = justificationAuthor;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CorrectionsResponse{" +
                "repositoryName='" + repositoryName + '\'' +
                ", commitsRevision='" + commitsRevision + '\'' +
                ", justification='" + justification + '\'' +
                ", justificationAuthor='" + justificationAuthor + '\'' +
                ", date=" + date +
                '}';
    }
}
