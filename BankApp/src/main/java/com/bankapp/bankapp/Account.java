package com.bankapp.bankapp;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "account")
public class Account implements Serializable {
    private int aid; // 'aid' = Account ID.
    private String name;
    private String surname;
    private String contactPhone;
    private String address;
    private double accBalance = 0; //default value 0
    private boolean activated = true; //default value true

    public Account(int aid, String name, String surname, String contactPhone, String address, double accBalance, boolean activated) {
        this.aid = aid;
        this.name = name;
        this.surname = surname;
        this.contactPhone = contactPhone;
        this.address = address;
        this.accBalance = accBalance;
        this.activated = activated;
    }

    public Account(String name, String surname, String contactPhone, String address) {
        this.name = name;
        this.surname = surname;
        this.contactPhone = contactPhone;
        this.address = address;
    }

    public Account() {
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(double accBalance) {
        this.accBalance = accBalance;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
