package com.bg.ebank.general.backing;

import com.bg.ebank.entity.User;
import com.bg.ebank.facade.AbstractFacade;
import com.bg.ebank.facade.UserFacade;
import org.apache.commons.codec.digest.DigestUtils;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Collection;
import java.util.List;


/**
 * Created by bg
 */
@ManagedBean
@ViewScoped
public class UserController extends AbstractController<User> {

    @EJB
    UserFacade userFacade;

    public UserController() {
        super(User.class);
    }

    @Override
    protected AbstractFacade<User> getEjbFacade() {
        return userFacade;
    }

    /**
     * Adds SHA-256 to the password. Needed for Glassfish 4 by default
     */
    @Override
    public void transformBeforeCreate() {
        super.transformBeforeCreate();
        selected.setPassword(DigestUtils.sha256Hex(selected.getPassword()));
    }
}
