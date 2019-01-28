package com.nastrsoft.commitChecks.model.response;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CommitsResponse {
    @NotNull
    private String revision;
    @NotNull
    private String author;
    @NotNull
    private LocalDate date;
    @NotNull
    private String message;
    @NotNull
    private String artifact;
    @NotNull
    private String svn_path;
    @NotNull
    private String artf_calc;
    @NotNull
    private String release;
    @NotNull
    private String type;

    public CommitsResponse() {
    }

    public CommitsResponse(String revision, String author, LocalDate date, String message, String artifact, String svn_path, String artf_calc, String release, String type) {
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
