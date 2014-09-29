package com.bg.ebank.servlets;


import com.bg.ebank.entity.Account;
import com.bg.ebank.entity.Groups;
import com.bg.ebank.entity.User;
import com.bg.ebank.facade.AccountFacade;
import com.bg.ebank.facade.GroupFacade;
import com.bg.ebank.facade.UserFacade;
import com.bg.ebank.general.backing.UserController;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "InitApp", urlPatterns = ("/initApp"))
public class InitServlet extends HttpServlet {

    @EJB
    UserFacade userFacade;

    @EJB
    GroupFacade groupFacade;

    @EJB
    AccountFacade accountFacade;

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        PrintWriter writer = httpServletResponse.getWriter();
        writer.append("Creating template database: ");

        try {
            List<User> users = new ArrayList<>();
            List<Groups> groupsList = new ArrayList<>();
            List<Account> accounts = new ArrayList<>();

            users.add(new User("Link", "Hero", "admin@ebank.com", "passwd"));
            users.add(new User("Abcd", "Alphabet", "user@ebank.com", "passwd"));

            groupsList.add(new Groups("admin", "Admins"));
            groupsList.add(new Groups("user", "Users"));

            for(Groups group : groupsList){
                groupFacade.create(group);
            }

            for(User user : users){
                userFacade.create(user);
            }

            users = userFacade.findAll();
            groupsList = groupFacade.findAll();

            users.get(0).getGroupsList().add(groupsList.get(0));
            groupsList.get(0).getUserList().add(users.get(0));

            users.get(1).getGroupsList().add(groupsList.get(1));
            groupsList.get(1).getUserList().add(users.get(1));

            accounts.add(new Account(1234555514141414l, new Date(), 12000, "USD"));
            accounts.add(new Account(1234555565656565l, new Date(), 18000, "USD"));
            accounts.get(0).setUser(users.get(0));
            accounts.get(1).setUser(users.get(1));

            for(Account account : accounts){
                accountFacade.create(account);
            }

            for(Groups group : groupsList){
                groupFacade.edit(group);
            }

            for(User user : users){
                userFacade.edit(user);
            }

            /**
             * Add accounts
             */


            writer.append("Completed.");

        }catch (Exception ex){
            writer.append(ex.toString());
        }
    }
}