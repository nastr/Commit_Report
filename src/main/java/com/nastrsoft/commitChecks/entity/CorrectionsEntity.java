package com.nastrsoft.commitChecks.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Corrections", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "releaseId", "commits_revision", "repository_name"})})
public class CorrectionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;
    @NotNull
    private Long releaseId;
    @NotNull
    private String repository_name;
    @NotNull
    private String commits_revision;
    @NotNull
    private String justification;   //Justification
    @NotNull
    private String author;          //Author
    @NotNull
    private LocalDate updated;      //Date

    public CorrectionsEntity() {
    }

    public CorrectionsEntity(@NotNull Long releaseId, @NotNull String repository_name, @NotNull String commits_revision, @NotNull String justification, @NotNull String author, @NotNull LocalDate updated) {
        this.releaseId = releaseId;
        this.repository_name = repository_name;
        this.commits_revision = commits_revision;
        this.justification = justification;
        this.author = author;
        this.updated = updated;
    }

    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
    }

    public String getRepository_name() {
        return repository_name;
    }

    public void setRepository_name(String repository_name) {
        this.repository_name = repository_name;
    }

    public String getCommits_revision() {
        return commits_revision;
    }

    public void setCommits_revision(String commits_revision) {
        this.commits_revision = commits_revision;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "CorrectionsEntity{" +
                "id=" + id +
                ", releaseId=" + releaseId +
                ", repository_name='" + repository_name + '\'' +
                ", commits_revision='" + commits_revision + '\'' +
                ", justification='" + justification + '\'' +
                ", author='" + author + '\'' +
                ", updated=" + updated +
                '}';
    }
}
