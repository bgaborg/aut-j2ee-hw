/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bg.ebank.facade;

import com.bg.ebank.entity.Account;
import com.bg.ebank.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author j2ee
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "ebank_jdbc")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }


    
}
