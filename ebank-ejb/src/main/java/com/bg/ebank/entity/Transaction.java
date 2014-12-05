/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bg.ebank.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author j2ee
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Transaction.getAllByAccountId",
                query = "SELECT t FROM Transaction t WHERE t.fromId = :accountId OR t.toId = :accountId"
        ),
        @NamedQuery(name = "Transaction.getByFromAccount",
                query = "SELECT t FROM Transaction t WHERE t.fromId = :fromId"
        ),
        @NamedQuery(name = "Transaction.getByToAccount",
                query = "SELECT t FROM Transaction t WHERE t.toId = :toId"
        ),
        @NamedQuery(name = "Transaction.getByAmount",
                query = "SELECT t FROM Transaction t WHERE t.amount = :amount"
        ),
        @NamedQuery(name = "Transaction.getByDate",
                query = "SELECT t FROM Transaction t WHERE t.date <= :toDate AND t.date >= :fromDate "
        )
})
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long fromId;

    private Long toId;

    @NotNull
    private String currency;

    @NotNull
    private double amount;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Transaction() {
    }

    public Transaction(Long fromId, Long toId, double amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
    }

    public Long getFromId() {
        return fromId;
    }

    public Long getToId() {
        return toId;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFromId(Long from) {
        this.fromId = from;
    }

    public void setToId(Long to) {
        this.toId = to;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", from=" + fromId +
                ", to=" + toId +
                ", currency=" + currency +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
