/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bg.ebank.facade;

import com.bg.ebank.entity.Account;
import com.bg.ebank.entity.User;
import com.bg.ebank.exceptions.BankException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author j2ee
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {
    @PersistenceContext(unitName = "ebank_jdbc")
    private EntityManager em;

    private static Logger logger = Logger.getLogger(AccountFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    public List<Account> findByUserId(String userId) {
        return em.createNamedQuery("Account.findByUserId")
                .setParameter("email", userId)
                .getResultList();
    }

    public List<Account> findExceptUserId(Long accountId, String currecy) {
        return em.createNamedQuery("Account.findAllExceptIdSameCurrency")
                .setParameter("accountId", accountId)
                .setParameter("currency", currecy)
                .getResultList();
    }

    @Override
    public void create(Account account) throws BankException {
        Random rand = new Random();
        int rn1 = 1234;
        int rn2 = 5555;
        int rn3 = rand.nextInt((9999 - 1000) + 1) + 1000;
        int rn4 = rand.nextInt((9999 - 1000) + 1) + 1000;

        Long id = Long.parseLong(rn1 + "" + rn2 + "" + rn3 + "" + rn4);
        if (account.getAccountId() == null || account.getAccountId() == 0)
            account.setAccountId(id);

        account.setCreatedate(new Date(System.currentTimeMillis()));

        em.persist(account);
    }
}
