package com.nastrsoft.commitChecks.back_end.entity;

import javax.validation.constraints.NotNull;

public class DashboardSummaryEntity {
    @NotNull
    private int in_scope;
    @NotNull
    private int not_in_scope;
    @NotNull
    private int justified;

    public DashboardSummaryEntity() {
    }

    public DashboardSummaryEntity(@NotNull int in_scope, @NotNull int not_in_scope, @NotNull int justified) {
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
