/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package com.bg.ebank.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author b
 */
@Entity
@Table(name = "GROUPS")
@NamedQueries({
    @NamedQuery(name = "Group.findAll", query = "SELECT g FROM Groups g"),
    @NamedQuery(name = "Group.findByName", query = "SELECT g FROM Groups g WHERE g.name = :name"),
    @NamedQuery(name = "Group.findByDescription", query = "SELECT g FROM Groups g WHERE g.description = :description")})
public class Groups implements Serializable {

    @Id
    @NotNull
    @Size(min = 1, max = 50, message="{group.name}")
    @Column(name = "name", unique = true)
    private String name;

    @Size(max = 300, message="{group.description}")
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "groupsList", fetch = FetchType.EAGER)
    private List<User> userList;

    public Groups() {
        this.userList = new ArrayList<>();
    }

    public Groups(String name, String description) {
        this.name = name;
        this.description = description;
        this.userList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> UserList) {
        this.userList = UserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
