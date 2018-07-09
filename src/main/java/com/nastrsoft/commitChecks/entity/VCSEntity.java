package com.nastrsoft.commitChecks.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vcs", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "name", "type"})})
public class VCSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private LocalDate updated;

    public VCSEntity(@NotNull String name, @NotNull String type, @NotNull LocalDate updated) {
        this.name = name;
        this.type = type;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }
}
