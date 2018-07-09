package com.nastrsoft.commitChecks.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Releases", uniqueConstraints = {@UniqueConstraint(columnNames = {"jira_version"})})
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Long id;
    @NotNull
    private String jira_version;
    @NotNull
    private String status;
    @Nullable
    private String release_name;
    @NotNull
    private LocalDate updated;

    public ReportEntity() {
    }

    public ReportEntity(@NotNull String jira_version, @NotNull String status, String release_name, @NotNull LocalDate updated) {
        this.jira_version = jira_version;
        this.status = status;
        this.release_name = release_name;
        this.updated = updated;
    }

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "ReportEntity{" +
                "id=" + id +
                ", jira_version='" + jira_version + '\'' +
                ", status='" + status + '\'' +
                ", release_name='" + release_name + '\'' +
                ", updated=" + updated +
                '}';
    }
}
