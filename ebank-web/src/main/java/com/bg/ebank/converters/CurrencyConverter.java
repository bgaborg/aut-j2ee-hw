package com.bg.ebank.converters;


import com.bg.ebank.util.JsfUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Currency;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

@FacesConverter(value = "currencyConverter")
public class CurrencyConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return Currency.getInstance(value);
    }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof Currency) {
            return object.toString();
        }else if(object instanceof Vector){
            Vector<Currency> userVector = (Vector<Currency>) object;
            StringBuffer sB = new StringBuffer();
            for(Currency u : userVector){
                sB.append(u.toString());
                sB.append("; ");
            }
            return sB.toString();
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Currency.class.getName()});
            return null;
        }
    }

}