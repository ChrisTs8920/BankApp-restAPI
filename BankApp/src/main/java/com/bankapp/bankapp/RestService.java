package com.bankapp.bankapp;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/bankService")
public class RestService {
    public static final String NOT_FOUND = "<h3>Error - Account not found.</h3>";
    public static final String NOT_ACTIVATED = "<h3>Error - Account not activated.</h3>";

    public static final String SUCCESS_CREATE = "<h3>Account created successfully.</h3>";
    public static final String ERROR_CREATE = "<h3>Error - Could not create account.</h3>";

    public static final String SUCCESS_DEPOSIT = "<h3>Successful deposit.</h3>";
    public static final String ERROR_DEPOSIT = "<h3>Deposit error.</h3>";

    public static final String SUCCESS_WITHDRAW = "<h3>Successful withdrawal.</h3>";
    public static final String ERROR_WITHDRAW = "<h3>Error - Insufficient Balance</h3>";

    public static final String SUCCESS_TRANSFER = "<h3>Transfer successful.</h3>";
    public static final String ERROR_TRANSFER = "<h3>Error - Could not transfer balance.<br>Check if balance is sufficient.</h3>";

    public static final String SUCCESS_STATUS = "<h3>Account status changed.</h3>";
    public static final String ERROR_STATUS = "<h3>Error - Could not change account status.</h3>";

    public static final String SUCCESS_DELETE = "<h3>Delete successful.</h3>";
    public static final String ERROR_DELETE = "<h3>Error - Could not delete account.</h3>";

    public static final String NEGATIVE_VAL = "<h3>Error - No negative values allowed.</h3>";

    //1
    @POST
    @Path("/addUser")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createUser(@FormParam("name") String name,
                             @FormParam("surname") String surname,
                             @FormParam("contactPhone") String contactPhone,
                             @FormParam("address") String address,
                             @Context HttpServletResponse servletResponse) {
        Account account = new Account(name, surname, contactPhone, address);
        if (AccountDao.addAccount(account) == 1) {
            return SUCCESS_CREATE;
        }
        else {
            return ERROR_CREATE;
        }
    }

    //2
    @PUT
    @Path("/deposit/{aid}")
    @Produces(MediaType.TEXT_HTML)
    public String deposit(@PathParam("aid") int aid, @QueryParam("amount")  double amount) {
        if (isNegative(amount)) {
            return NEGATIVE_VAL;
        }
        //account checks
        if (!AccountDao.AccExists(aid)) {
            return NOT_FOUND;
        }
        if (!AccountDao.IsActivated(aid)) {
            return NOT_ACTIVATED;
        }

        if (AccountDao.deposit(aid, amount) == 1) {
            return SUCCESS_DEPOSIT;
        }
        else {
            return ERROR_DEPOSIT;
        }
    }

    //3
    @PUT
    @Path("/withdraw/{aid}")
    @Produces(MediaType.TEXT_HTML)
    public String withdraw(@PathParam("aid") int aid, @QueryParam("amount") double amount) {
        if (isNegative(amount)) {
            return NEGATIVE_VAL;
        }
        //account checks
        if (!AccountDao.AccExists(aid)) {
            return NOT_FOUND;
        }
        if (!AccountDao.IsActivated(aid)) {
            return NOT_ACTIVATED;
        }

        if (AccountDao.withdraw(aid, amount) == 1) {
            return SUCCESS_WITHDRAW;
        }
        else {
            return ERROR_WITHDRAW;
        }
    }

    //4
    @PUT
    @Path("/transferBalance/{aid1}")
    @Produces(MediaType.TEXT_HTML)
    public String transferBalance(@PathParam("aid1") int aid1, @QueryParam("aid2") int aid2,
                                  @QueryParam("amount") double amount) {
        if (isNegative(amount)) {
            return NEGATIVE_VAL;
        }
        //account checks
        if (!(AccountDao.AccExists(aid1) && AccountDao.AccExists(aid2))) {
            return NOT_FOUND;
        }
        if (!(AccountDao.IsActivated(aid1) && AccountDao.IsActivated(aid2))) {
            return NOT_ACTIVATED;
        }

        //transfers from aid1 to aid2.
        if (AccountDao.transferBalance(aid1, aid2, amount) == 2) {
            return SUCCESS_TRANSFER;
        }
        else {
            return ERROR_TRANSFER;
        }
    }

    //5
    @PUT
    @Path("/updateAccountStatus/{aid}/{status}")
    @Produces(MediaType.TEXT_HTML)
    public String updateStatus(@PathParam("aid") int aid, @PathParam("status") String status) {
        if (!AccountDao.AccExists(aid)) {
            return NOT_FOUND;
        }

        if (AccountDao.updateStatus(status, aid) == 1) {
            return SUCCESS_STATUS + "<p>Account " + status + "d.</p>"; //Account activate-d. || Account deactivate-d.
        }
        else {
            return ERROR_STATUS;
        }
    }

    //6
    @GET
    @Path("/allAccounts_json/{aid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccount_json(@PathParam("aid") int aid) {
        return AccountDao.getAccount(aid);
    }

    @GET
    @Path("/allAccounts_xml/{aid}")
    @Produces(MediaType.APPLICATION_XML)
    public Account getAccount_xml(@PathParam("aid") int aid) {
        return AccountDao.getAccount(aid);
    }

    //7
    @GET
    @Path("/allAccounts_json")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Account> getAccounts_json() {
        return AccountDao.getAllAccounts();
    }

    @GET
    @Path("/allAccounts_xml")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Account> getAccounts_xml() {
        return AccountDao.getAllAccounts();
    }

    //8
    @DELETE
    @Path("/delete/{aid}")
    @Produces(MediaType.TEXT_HTML)
    public String deleteAccount(@PathParam("aid") int aid) {
        if (!AccountDao.AccExists(aid)) {
            return NOT_FOUND;
        }

        if (AccountDao.deleteAccount(aid) == 1) {
            return SUCCESS_DELETE;
        }
        return ERROR_DELETE;
    }

    private boolean isNegative(double amount) {
        return amount < 0;
    }
}