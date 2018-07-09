package com.nastrsoft.commitChecks.model.response;

import javax.validation.constraints.NotNull;

public class BranchesResponse {
    @NotNull
    private String repository_name;
    @NotNull
    private String path;
    @NotNull
    private String trusted_base_line;
    @NotNull
    private String current_base_line;

    public BranchesResponse() {
    }

    public BranchesResponse(String repository_name, String path, String trusted_base_line, String current_base_line) {
        this.repository_name = repository_name;
        this.path = path;
        this.trusted_base_line = trusted_base_line;
        this.current_base_line = current_base_line;
    }

    public String getRepository_name() {
        return repository_name;
    }

    public void setRepository_name(String repository_name) {
        this.repository_name = repository_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTrusted_base_line() {
        return trusted_base_line;
    }

    public void setTrusted_base_line(String trusted_base_line) {
        this.trusted_base_line = trusted_base_line;
    }

    public String getCurrent_base_line() {
        return current_base_line;
    }

    public void setCurrent_base_line(String current_base_line) {
        this.current_base_line = current_base_line;
    }
}
