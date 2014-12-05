package com.bg.ebank.facade;

import com.bg.ebank.entity.Account;
import com.bg.ebank.entity.Transaction;
import com.bg.ebank.exceptions.BankException;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Date;
import java.util.List;

@Stateless
public class TransactionFacade extends AbstractFacade<Transaction> {
    @PersistenceContext(unitName = "ebank_jdbc")
    private EntityManager em;

    @Resource
    EJBContext ejbContext;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransactionFacade() {
        super(Transaction.class);
    }

    public void createExternal(Transaction transaction) throws BankException {
        transaction.setDate(new Date());
        em.persist(transaction);
    }

    @Override
    public void create(Transaction transaction) throws BankException {
        try {
            Long fromId = transaction.getFromId();
            Long toId = transaction.getToId();

            Account from = em.find(Account.class, fromId);
            Account to = em.find(Account.class, toId);

            if (!from.getCurrency().equals(to.getCurrency())) {
                throw new BankException("From and to currencies should be equal.");
            }
            from.decrease(transaction.getAmount());
            to.increase(transaction.getAmount());

            transaction.setDate(new Date());
            transaction.setCurrency(from.getCurrency());

            em.persist(transaction);
        }catch (BankException e){
            ejbContext.setRollbackOnly();
            throw e;
        }catch (Exception e){
            ejbContext.setRollbackOnly();
            throw new BankException(e.getMessage());
        }
    }

    @Override
    public void edit(Transaction entity) throws BankException {
        throw new BankException("Transactions can not be edited");
    }

    @Override
    public void remove(Transaction transaction) throws BankException {
        Long fromId = transaction.getFromId();
        Long toId = transaction.getToId();

        Account from = em.find(Account.class, fromId);
        Account to = em.find(Account.class, toId);

        if (!from.getCurrency().equals(to.getCurrency())) {
            throw new BankException("From and to currencies should be equal.");
        }

        from.increase(transaction.getAmount());
        to.decrease(transaction.getAmount());
        em.merge(from);
        em.merge(to);
        getEntityManager().remove(em.merge(transaction));
    }

    public List<Transaction> getAllByAccountId(Long accountId) {
        return em.createNamedQuery("Transaction.getAllByAccountId")
                .setParameter("accountId", accountId)
                .getResultList();
    }

    public List<Transaction> getByFromAccount(Long fromAccountId){
        return em.createNamedQuery("Transaction.getByFromAccount")
                .setParameter("fromId", fromAccountId)
                .getResultList();
    }

    public List<Transaction> getByToAccount(Long toAccountId){
        return em.createNamedQuery("Transaction.getByToAccount")
                .setParameter("toId", toAccountId)
                .getResultList();
    }

    public List<Transaction> getByAmount(double amount){
        return em.createNamedQuery("Transaction.getByAmount")
                .setParameter("amount", amount)
                .getResultList();
    }


    public List<Transaction> getByDate(Date from, Date to){
        return em.createNamedQuery("Transaction.getByDate")
                .setParameter("fromDate", from)
                .setParameter("toDate", to)
                .getResultList();
    }


}
