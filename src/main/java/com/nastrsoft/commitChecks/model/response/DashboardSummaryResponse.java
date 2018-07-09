package com.nastrsoft.commitChecks.model.response;

public class DashboardSummaryResponse {
    private int in_scope;
    private int not_in_scope;
    private int justified;

    public DashboardSummaryResponse() {
    }

    public DashboardSummaryResponse(int in_scope, int not_in_scope, int justified) {
        this.in_scope = in_scope;
        this.not_in_scope = not_in_scope;
        this.justified = justified;
    }

    public int getIn_scope() {
        return in_scope;
    }

    public void setIn_scope(int in_scope) {
        this.in_scope = in_scope;
    }

    public int getNot_in_scope() {
        return not_in_scope;
    }

    public void setNot_in_scope(int not_in_scope) {
        this.not_in_scope = not_in_scope;
    }

    public int getJustified() {
        return justified;
    }

    public void setJustified(int justified) {
        this.justified = justified;
    }
}
