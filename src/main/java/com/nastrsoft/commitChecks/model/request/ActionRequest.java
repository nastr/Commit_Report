package com.nastrsoft.commitChecks.model.request;

public class ActionRequest {
    private String jira_version;
    private String action;

    public ActionRequest() {
    }

    public ActionRequest(String jira_version, String action) {
        this.jira_version = jira_version;
        this.action = action;
    }

    public String getJira_version() {
        return jira_version;
    }

    public void setJira_version(String jira_version) {
        this.jira_version = jira_version;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ActionRequest{" +
                "jira_version='" + jira_version + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
