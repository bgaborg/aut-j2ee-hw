package com.bg.ebank.converters;

import com.bg.ebank.entity.Account;
import com.bg.ebank.facade.AccountFacade;
import com.bg.ebank.util.JsfUtil;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bg
 */

@FacesConverter(value = "accountConverter")
public class AccountConverter implements Converter {

    @EJB
    private AccountFacade ejbFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.ejbFacade.find(getKey(value));
    }

    Long getKey(String value) {
        Long key = Long.valueOf(value);
        return key;
    }

    String getStringKey(Long value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof Account) {
            Account o = (Account) object;
            return getStringKey(o.getAccountId());
        } else if (object instanceof Vector) {
            Vector<Account> accountVector = (Vector<Account>) object;
            StringBuffer sB = new StringBuffer();
            for (Account a : accountVector) {
                sB.append(a.toString());
                sB.append("; ");
            }
            return sB.toString();
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Account.class.getName()});
            return null;
        }
    }

}
