package com.nastrsoft.commitChecks.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Commits", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "revision", "svn_path"})})
public class CommitsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;
    @NotNull
//    @ManyToOne
    private Long releaseId;
    @NotNull
    private String revision;
    @NotNull
    private String author;
    @NotNull
    private LocalDate date;
    @NotNull
    private String message;
    @Nullable
    private String artifact;
    @NotNull
    private String svn_path;
    @NotNull
    private String artf_calc;
    @NotNull
    @Column(name = "jira_version")
    private String release;
    @Nullable
    private String type;

    public CommitsEntity() {
    }

    public CommitsEntity(@NotNull Long releaseId, @NotNull String revision, @NotNull String author, @NotNull LocalDate date, @NotNull String message, String artifact, @NotNull String svn_path, @NotNull String artf_calc, @NotNull String release, String type) {
        this.releaseId = releaseId;
        this.revision = revision;
        this.author = author;
        this.date = date;
        this.message = message;
        this.artifact = artifact;
        this.svn_path = svn_path;
        this.artf_calc = artf_calc;
        this.release = release;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getSvn_path() {
        return svn_path;
    }

    public void setSvn_path(String svn_path) {
        this.svn_path = svn_path;
    }

    public String getArtf_calc() {
        return artf_calc;
    }

    public void setArtf_calc(String artf_calc) {
        this.artf_calc = artf_calc;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
