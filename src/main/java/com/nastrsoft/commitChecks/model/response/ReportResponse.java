package com.nastrsoft.commitChecks.model.response;

import java.time.LocalDate;

public class ReportResponse {

    private String jira_version;
    private String status;
    private String release_name;
    private LocalDate updated;

    public ReportResponse() {
    }

    public ReportResponse(String jira_version, String status, String release_name, LocalDate updated) {
        this.jira_version = jira_version;
        this.status = status;
        this.release_name = release_name;
        this.updated = updated;
    }

    public String getJira_version() {
        return jira_version;
    }

    public void setJira_version(String jira_version) {
        this.jira_version = jira_version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelease_name() {
        return release_name;
    }

    public void setRelease_name(String release_name) {
        this.release_name = release_name;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }
}
