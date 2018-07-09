package com.nastrsoft.commitChecks.entity;

import javax.persistence.*;

@Entity
@Table(name = "Credentials", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "value"})})
public class CredentialsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;
    private String name;
    private String value;

    public CredentialsEntity() {
    }

    public CredentialsEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
