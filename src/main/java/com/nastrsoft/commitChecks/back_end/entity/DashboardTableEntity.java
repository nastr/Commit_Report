package com.nastrsoft.commitChecks.back_end.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//@Table(name = "DashboardTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"commitsRevision", "repositoryName"})})
public class DashboardTableEntity {
    private String commitsRevision;
    private String repositoryName;
    private String commitsMessage;
    private String jiraArtifact;        //Artifact
    private String jiraVersion;         //Release
    private String jiraTitle;           //Title
    private String jiraParentArtifact;  //Parent
    private String scope;               //Scope
    private String jiraType;            //Type
    private String status;              //Status
    private String commitsAuthor;      //Commits authors

    public DashboardTableEntity() {
    }

    public DashboardTableEntity(String commitsRevision, String repositoryName, String commitsMessage, String jiraArtifact, String jiraVersion, String jiraTitle, String jiraParentArtifact, String scope, String jiraType, String status, String commitsAuthor) {
        this.commitsRevision = commitsRevision;
        this.repositoryName = repositoryName;
        this.commitsMessage = commitsMessage;
        this.jiraArtifact = jiraArtifact;
        this.jiraVersion = jiraVersion;
        this.jiraTitle = jiraTitle;
        this.jiraParentArtifact = jiraParentArtifact;
        this.scope = scope;
        this.jiraType = jiraType;
        this.status = status;
        this.commitsAuthor = commitsAuthor;
    }

    public String getCommitsRevision() {
        return commitsRevision;
    }

    public void setCommitsRevision(String commitsRevision) {
        this.commitsRevision = commitsRevision;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getCommitsMessage() {
        return commitsMessage;
    }

    public void setCommitsMessage(String commitsMessage) {
        this.commitsMessage = commitsMessage;
    }

    public String getJiraArtifact() {
        return jiraArtifact;
    }

    public void setJiraArtifact(String jiraArtifact) {
        this.jiraArtifact = jiraArtifact;
    }

    public String getJiraVersion() {
        return jiraVersion;
    }

    public void setJiraVersion(String jiraVersion) {
        this.jiraVersion = jiraVersion;
    }

    public String getJiraTitle() {
        return jiraTitle;
    }

    public void setJiraTitle(String jiraTitle) {
        this.jiraTitle = jiraTitle;
    }

    public String getJiraParentArtifact() {
        return jiraParentArtifact;
    }

    public void setJiraParentArtifact(String jiraParentArtifact) {
        this.jiraParentArtifact = jiraParentArtifact;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJiraType() {
        return jiraType;
    }

    public void setJiraType(String jiraType) {
        this.jiraType = jiraType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommitsAuthor() {
        return commitsAuthor;
    }

    public void setCommitsAuthor(String commitsAuthor) {
        this.commitsAuthor = commitsAuthor;
    }

    @Override
    public String toString() {
        return "DashboardTableEntity{" +
                "commitsRevision='" + commitsRevision + '\'' +
                ", repositoryName='" + repositoryName + '\'' +
                ", commitsMessage='" + commitsMessage + '\'' +
                ", jiraArtifact='" + jiraArtifact + '\'' +
                ", jiraVersion='" + jiraVersion + '\'' +
                ", jiraTitle='" + jiraTitle + '\'' +
                ", jiraParentArtifact='" + jiraParentArtifact + '\'' +
                ", scope='" + scope + '\'' +
                ", jiraType='" + jiraType + '\'' +
                ", status='" + status + '\'' +
                ", commitsAuthor='" + commitsAuthor + '\'' +
                '}';
    }
}
