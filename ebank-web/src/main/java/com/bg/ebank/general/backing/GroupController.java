package com.bg.ebank.general.backing;

import com.bg.ebank.entity.Groups;
import com.bg.ebank.facade.AbstractFacade;
import com.bg.ebank.facade.GroupFacade;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by bg
 */
@ManagedBean
@ViewScoped
public class GroupController extends AbstractController<Groups> {

    @EJB
    GroupFacade groupFacade;

    public GroupController() {
        super(Groups.class);
    }


    @Override
    protected AbstractFacade<Groups> getEjbFacade() {
        return groupFacade;
    }
}
