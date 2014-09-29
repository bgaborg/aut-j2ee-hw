/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bg.ebank.entity;

import com.bg.ebank.exceptions.BankException;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author bg
 */
@Entity
@Table(name = "ACCOUNT")
@NamedQueries({
        @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
        @NamedQuery(name = "Account.findByAccountid", query = "SELECT a FROM Account a WHERE a.accountId = :accountid"),
        @NamedQuery(name = "Account.findByCreatedate", query = "SELECT a FROM Account a WHERE a.createdate = :createdate"),
        @NamedQuery(name = "Account.findByUserId", query = "SELECT a FROM Account AS a WHERE a.user.email = :email"),
        @NamedQuery(name = "Account.findAllExceptIdSameCurrency", query =
                "SELECT a FROM Account as a " +
                        "WHERE a.accountId != :accountId AND a.currency = :currency")})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Basic(optional = false)
    @Column(name = "CREATEDATE")
    @Temporal(TemporalType.TIMESTAMP )
    private Date createdate;

    @Basic(optional = false)
    @Column(name = "BALANCE")
    private double balance;

    @Basic(optional = false)
    @Column(name = "CURRENCY")
    private String currency;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Account() {
    }


    public Account(Long accountId, Date createdate, double balance, String currency) {
        this.accountId = accountId;
        this.createdate = createdate;
        this.balance = balance;
        this.currency = currency;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountid) {
        this.accountId = accountid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setClient(User user) {
        this.user = user;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", currency=" + currency +
                ", user=" + user +
                '}';
    }

    public void increase(double amount) {
        this.balance += amount;
    }

    public void decrease(double amount) throws BankException {
        if (amount > balance)
            throw new BankException("Balance on account too small");
        this.balance -= amount;
    }

}
