package com.bg.ebank.ws;

import com.bg.ebank.entity.Account;
import com.bg.ebank.entity.Transaction;
import com.bg.ebank.exceptions.BankException;
import com.bg.ebank.facade.AccountFacade;
import com.bg.ebank.facade.TransactionFacade;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService(name = "EbankWS")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EbankWS {
    @EJB
    TransactionFacade transactionFacade;

    @EJB
    AccountFacade accountFacade;

    @WebMethod
    public Transaction decrease(long fromId, long toId, int amount) throws BankException {
        try {
            Account fromAccount = accountFacade.find(fromId);
            if (fromAccount == null) {
                throw new BankException("No account found with id: " + fromId);
            }

            Transaction transaction = new Transaction();
            transaction.setFromId(fromId);
            transaction.setToId(toId);
            transaction.setAmount(amount);
            transaction.setCurrency(fromAccount.getCurrency());
            fromAccount.decrease(amount);

            transactionFacade.createExternal(transaction);
            accountFacade.edit(fromAccount);

            return transaction;
        } catch (BankException be) {
            throw be;
        } catch (Exception e) {
            throw new BankException(e.getMessage());
        }
    }

    @WebMethod
    public Transaction increase(long fromId, long toId, int amount) throws BankException {
        try {
            Account toAccount = accountFacade.find(toId);

            if (toAccount == null) {
                throw new BankException("No account found with id: " + toId);
            }

            Transaction transaction = new Transaction();
            transaction.setFromId(fromId);
            transaction.setToId(toId);
            transaction.setAmount(amount);
            transaction.setCurrency(toAccount.getCurrency());
            toAccount.increase(amount);

            transactionFacade.createExternal(transaction);
            accountFacade.edit(toAccount);
            return transaction;
        } catch (BankException be) {
            throw be;
        } catch (Exception e) {
            throw new BankException(e.getMessage());
        }

    }

}
