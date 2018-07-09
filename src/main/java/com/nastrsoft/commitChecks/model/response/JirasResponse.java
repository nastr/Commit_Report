package com.nastrsoft.commitChecks.model.response;

public class JirasResponse {
    private String key;
    private String title;
    private String type;
    private String status;
    private String assignee;
    private String reporter;
    private String parent;
    private String sub_task;
    private String affects_versions;
    private String fix_versions;
    private String sprint;

    public JirasResponse() {
    }

    public JirasResponse(String key, String title, String type, String status, String assignee, String reporter, String parent, String sub_task, String affects_versions, String fix_versions, String sprint) {
        this.key = key;
        this.title = title;
        this.type = type;
        this.status = status;
        this.assignee = assignee;
        this.reporter = reporter;
        this.parent = parent;
        this.sub_task = sub_task;
        this.affects_versions = affects_versions;
        this.fix_versions = fix_versions;
        this.sprint = sprint;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSub_task() {
        return sub_task;
    }

    public void setSub_task(String sub_task) {
        this.sub_task = sub_task;
    }

    public String getAffects_versions() {
        return affects_versions;
    }

    public void setAffects_versions(String affects_versions) {
        this.affects_versions = affects_versions;
    }

    public String getFix_versions() {
        return fix_versions;
    }

    public void setFix_versions(String fix_versions) {
        this.fix_versions = fix_versions;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }
}
