/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bg.ebank.entity;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author ievans
 */
@Entity
@Table(name = "USER")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT p FROM User p"),
        @NamedQuery(name = "User.findById", query = "SELECT p FROM User p WHERE p.userId = :userId"),
        @NamedQuery(name = "User.findByFirstname", query = "SELECT p FROM User p WHERE p.firstname = :firstname"),
        @NamedQuery(name = "User.findByLastname", query = "SELECT p FROM User p WHERE p.lastname = :lastname"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT p FROM User p WHERE p.email = :email")})
public class User implements Serializable {

    @JoinTable(name = "USER_GROUPS",
            joinColumns = {@JoinColumn(name = "USER_EMAIL", referencedColumnName = "USER_EMAIL")},
            inverseJoinColumns = { @JoinColumn(name = "GROUP_NAME")})
    @ManyToMany(fetch=FetchType.EAGER)
    protected List<Groups> groupsList;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    protected List<Account> accountList;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    protected Integer userId;

    @Basic(optional = false)
    @Size(min = 1, max = 50, message = "{User.firstname}")
    @Column(name = "FIRST_NAME")
    protected String firstname;

    @Basic(optional = false)
    @Size(min = 1, max = 100, message = "{User.lastname}")
    @Column(name = "LAST_NAME")
    protected String lastname;

    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "{User.email}")
    @Size(min = 1, max = 45, message = "{User.email}")
    @Basic(optional = false)
    @Column(name = "USER_EMAIL", unique = true)
    protected String email;

    @Basic(optional = false)
    @Size(min = 1, max = 100, message = "{User.password}")
    @Column(name = "PASSWORD")
    protected String password;

    public User() {
        this.groupsList = new ArrayList<Groups>();
        this.accountList = new ArrayList<>();
    }

    public User(String firstName,
                String lastName,
                String email,
                String password) {
        this();
        this.firstname = firstName;
        this.lastname = lastName;
        this.email = email;
        this.password = DigestUtils.sha256Hex(password);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer id) {
        this.userId = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Add XmlTransient annotation to this field for security reasons.
     *
     * @return the password
     */
    @XmlTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Groups> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(List<Groups> groupsList) {
        this.groupsList = groupsList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", userId=" + userId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

}
