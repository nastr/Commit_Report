package com.nastrsoft.commitChecks.model.response;

import javax.validation.constraints.NotNull;

public class ActionResponse {
    @NotNull
    private String jira_version;
    @NotNull
    private String status;

    public ActionResponse() {
    }

    public ActionResponse(String jira_version, String status) {
        this.jira_version = jira_version;
        this.status = status;
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
}
