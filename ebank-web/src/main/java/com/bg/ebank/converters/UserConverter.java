package com.bg.ebank.converters;


import com.bg.ebank.entity.User;
import com.bg.ebank.facade.UserFacade;
import com.bg.ebank.util.JsfUtil;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

@FacesConverter(value = "userConverter")
public class UserConverter implements Converter {

    @EJB
    private UserFacade ejbFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.ejbFacade.find(getKey(value));
    }

    Integer getKey(String value) {
        Integer key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Integer value) {
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
        if (object instanceof User) {
            User o = (User) object;
            return getStringKey(o.getUserId());
        }else if(object instanceof Vector){
            Vector<User> userVector = (Vector<User>) object;
            StringBuffer sB = new StringBuffer();
            for(User u : userVector){
                sB.append(u.toString());
                sB.append("; ");
            }
            return sB.toString();
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), User.class.getName()});
            return null;
        }
    }

}