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
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class TransactionController extends AbstractController<Transaction> {

    @EJB
    TransactionFacade transactionFacade;

    @EJB
    AccountFacade accountFacade;

    List<Transaction> displayUserTransactions;
    Account userSelectedAccount;

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

    String searchText;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void searchAllFields(){
        System.out.println(searchText);
        displayUserTransactions = transactionFacade.searchByAllFieldsLike(searchText);
    }
}
