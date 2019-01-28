package com.nastrsoft.commitChecks.model.request;

public class ReleasesRequest {

    private String jira_version;
    private String release_name;

//    private String status;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    private LocalDate updated;

    public ReleasesRequest() {
    }

    public ReleasesRequest(String jira_version, String release_name) {
        this.jira_version = jira_version;
        this.release_name = release_name;
    }

    public String getJira_version() {
        return jira_version;
    }

    public void setJira_version(String jira_version) {
        this.jira_version = jira_version;
    }

    public String getRelease_name() {
        return release_name;
    }

    public void setRelease_name(String release_name) {
        this.release_name = release_name;
    }
}
