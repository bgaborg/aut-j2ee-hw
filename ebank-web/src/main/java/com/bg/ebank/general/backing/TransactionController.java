package com.bg.ebank.general.backing;


import com.bg.ebank.entity.Account;
import com.bg.ebank.entity.Transaction;
import com.bg.ebank.exceptions.BankException;
import com.bg.ebank.facade.AbstractFacade;
import com.bg.ebank.facade.AccountFacade;
import com.bg.ebank.facade.TransactionFacade;
import org.primefaces.event.FlowEvent;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.*;

@ManagedBean
@ViewScoped
public class TransactionController extends AbstractController<Transaction> {

    @EJB
    TransactionFacade transactionFacade;

    @EJB
    AccountFacade accountFacade;

    List<Transaction> displayUserTransactions;
    Account userSelectedAccount = null;

    public TransactionController() {
        super(Transaction.class);
    }

    @Override
    protected AbstractFacade<Transaction> getEjbFacade() {
        return transactionFacade;
    }

    public void updateTransactionsOfUser() {
        if (userSelectedAccount != null) {
            displayUserTransactions = transactionFacade.getAllByAccountId(userSelectedAccount.getAccountId());
        } else {
            displayUserTransactions = new ArrayList<>();
        }
    }

    public List<Transaction> getDisplayUserTransactions() {
        return displayUserTransactions;
    }

    public void setDisplayUserTransactions(List<Transaction> displayUserTransactions) {
        this.displayUserTransactions = displayUserTransactions;
    }

    public Account getUserSelectedAccount() {
        return userSelectedAccount;
    }

    public void setUserSelectedAccount(Account userSelectedAccount) {
        this.userSelectedAccount = userSelectedAccount;
    }

    /**
     * Transaction Wizard
     */
    Transaction wizardTransaction = new Transaction();
    boolean wizardFinished = false;

    public boolean isWizardFinished() {
        return wizardFinished;
    }

    public void sourceAccountUpdated() {
        if (wizardTransaction.getFromId() != null) {
            final Account account = accountFacade.find(wizardTransaction.getFromId());
            wizardTransaction.setCurrency(account.getCurrency());
        }
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public Transaction getWizardTransaction() {
        return wizardTransaction;
    }

    public Account getWizardSource(){
        Account a = null;
        if(wizardTransaction.getFromId() > 0){
            a = accountFacade.find(wizardTransaction.getFromId());
        }
        return a;
    }

    public Account getWizardTarget(){
        Account a = null;
        if(wizardTransaction.getToId() > 0){
            a = accountFacade.find(wizardTransaction.getToId());
        }
        return a;
    }

    public void setWizardTransaction(Transaction wizardTransaction) {
        this.wizardTransaction = wizardTransaction;
    }

    public void saveWizardTransaction() {
        try {
            transactionFacade.create(wizardTransaction);
            wizardFinished = true;
        } catch (BankException e) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Error", e.getMessage()));
            e.printStackTrace();
        }
    }

    Long sFromAccount;
    Long sToAccount;
    Double sAmount;
    Date sFromDate = new Date();
    Date sToDate = new Date();


    public Long getsFromAccount() {
        return sFromAccount;
    }

    public void setsFromAccount(Long sFromAccount) {
        this.sFromAccount = sFromAccount;
    }

    public Long getsToAccount() {
        return sToAccount;
    }

    public void setsToAccount(Long sToAccount) {
        this.sToAccount = sToAccount;
    }

    public Double getsAmount() {
        return sAmount;
    }

    public void setsAmount(Double sAmount) {
        this.sAmount = sAmount;
    }

    public Date getsFromDate() {
        return sFromDate;
    }

    public void setsFromDate(Date sFromDate) {
        this.sFromDate = sFromDate;
    }

    public Date getsToDate() {
        return sToDate;
    }

    public void setsToDate(Date sToDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sToDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        this.sToDate = calendar.getTime();
    }

    public void searchFromAccount(){
        displayUserTransactions = transactionFacade.getByFromAccount(sFromAccount);
    }

    public void searchToAccount(){
        displayUserTransactions = transactionFacade.getByToAccount(sToAccount);
    }

    public void searchAmount(){
        displayUserTransactions = transactionFacade.getByAmount(sAmount);
    }

    public void searchDate(){
        displayUserTransactions = transactionFacade.getByDate(sFromDate, sToDate);
    }

}
