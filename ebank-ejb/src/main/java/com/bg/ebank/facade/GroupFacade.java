/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bg.ebank.facade;

import com.bg.ebank.entity.Groups;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GroupFacade extends AbstractFacade<Groups> {
    @PersistenceContext(unitName = "ebank_jdbc")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupFacade() {
        super(Groups.class);
    }

}
