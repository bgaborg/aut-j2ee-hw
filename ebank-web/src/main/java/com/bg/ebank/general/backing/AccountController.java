package com.bg.ebank.general.backing;

import com.bg.ebank.entity.Account;
import com.bg.ebank.facade.AbstractFacade;
import com.bg.ebank.facade.AccountFacade;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@ManagedBean
@ViewScoped
public class AccountController extends AbstractController<Account> {
    @EJB
    AccountFacade accountFacade;


    public AccountController() {
        super(Account.class);
    }


    @Override
    protected AbstractFacade<Account> getEjbFacade() {
        return accountFacade;
    }

    public List<Long> getAccountIdsOfUser() {
        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return convertToLongId(accountFacade.findByUserId(userPrincipal.getName()));
    }

    public List<Account> getAccountsOfUser() {
        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return accountFacade.findByUserId(userPrincipal.getName());
    }

    public List<Long> getAccountIdsExceptUserSameCurrency(Long accountId, String currency) {
        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return convertToLongId(accountFacade.findExceptUserId(accountId, currency));
    }

    public List<Long> getAllIds() {
        return convertToLongId(accountFacade.findAll());
    }


    private List<Long> convertToLongId(List<Account> accounts) {
        List<Long> idList = new ArrayList<>();
        for (Account a : accounts) {
            idList.add(a.getAccountId());
        }
        return idList;
    }

}
