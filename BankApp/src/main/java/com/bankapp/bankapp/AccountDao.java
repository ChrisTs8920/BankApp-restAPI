package com.bankapp.bankapp;

import java.sql.*;
import java.util.ArrayList;

public class AccountDao {
    private static Connection getConnection() {
        Connection con = null;

        //connection with database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_accs",
                    "TSOUCHLAKIS", "1234");

        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static boolean AccExists(int aid) {
        boolean status = false;
        Connection con = AccountDao.getConnection();

        try {
            //sql statement that checks if aid exists in database
            PreparedStatement ps = con.prepareStatement("SELECT aid FROM accs WHERE aid = ?");
            ps.setInt(1, aid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = true;
            }

            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static boolean IsActivated(int aid) {
        boolean status = false;
        Connection con = AccountDao.getConnection();

        try {
            //sql statement that checks if aid is activated
            PreparedStatement ps = con.prepareStatement("SELECT activated FROM accs WHERE aid = ?");
            ps.setInt(1, aid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getBoolean(1);
            }

            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int deposit(int aid, double amount) {
        int status = 0;
        Connection con = AccountDao.getConnection();

        try {
            //sql statement that updates balance of aid
            PreparedStatement ps = con.prepareStatement("UPDATE accs SET accBalance = accBalance + ? WHERE aid = ?");
            ps.setDouble(1, amount);
            ps.setInt(2, aid);
            status = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int withdraw(int aid, double amount) {
        int status = 0;
        double balance = 0;
        Connection con = AccountDao.getConnection();

        try {
            //sql statement that returns balance of aid
            PreparedStatement ps = con.prepareStatement("SELECT accBalance FROM accs WHERE aid = ?");
            ps.setInt(1, aid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble(1);
            }
            //if balance of aid is less than amount, return 0
            if (balance < amount) {
                return status;
            }


            //sql statement that updates balance of aid
            ps = con.prepareStatement("UPDATE accs SET accBalance = accBalance - ? WHERE aid = ?");
            ps.setDouble(1, amount);
            ps.setInt(2, aid);
            status = ps.executeUpdate();

            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int addAccount(Account account) {
        int status = 0;
        Connection con = AccountDao.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO accs(name, surname, contactPhone, address)" +
                    " VALUES(?, ?, ?, ?)");
            ps.setString(1, account.getName());
            ps.setString(2, account.getSurname());
            ps.setString(3, account.getContactPhone());
            ps.setString(4, account.getAddress());
            status = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accountList = new ArrayList<>();
        Connection con = AccountDao.getConnection();

        //sql statement that returns all accounts
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM accs");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account acc = new Account(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getDouble(6),
                        rs.getBoolean(7));

                accountList.add(acc);
            }
            ps.close();
            rs.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public static Account getAccount(int aid) {
        Account account = new Account();
        Connection con = AccountDao.getConnection();

        //sql statement that returns account based on aid
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM accs WHERE aid= ?");
            ps.setInt(1, aid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account.setAid(rs.getInt(1));
                account.setName(rs.getString(2));
                account.setSurname(rs.getString(3));
                account.setContactPhone(rs.getString(4));
                account.setAddress(rs.getString(5));
                account.setAccBalance(rs.getDouble(6));
                account.setActivated(rs.getBoolean(7));
            }
            ps.close();
            rs.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public static int transferBalance(int aid1, int aid2, double amount) {
        int status = 0;
        double balance1 = 0;
        Connection con = AccountDao.getConnection();

        try {
            //sql statement that returns balance of aid1
            PreparedStatement ps = con.prepareStatement("SELECT accBalance FROM accs WHERE aid = ?");
            ps.setInt(1, aid1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                balance1 = rs.getDouble(1);
            }
            //if balance of aid1 is less than amount, return 0
            if (balance1 < amount) {
                return status;
            }

            //sql statement that updates balance of aid1
            ps = con.prepareStatement("UPDATE accs SET accBalance = accBalance - ? WHERE aid = ?");
            ps.setDouble(1, amount);
            ps.setInt(2, aid1);
            status += ps.executeUpdate();

            //sql statement that updates balance of aid2
            ps = con.prepareStatement("UPDATE accs SET accBalance = accBalance + ? WHERE aid = ?");
            ps.setDouble(1, amount);
            ps.setInt(2, aid2);
            status += ps.executeUpdate();

            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int updateStatus(String s, int aid) {
        int status = 0;
        boolean act;

        //check if the given string is either activate or deactivate
        if (s.trim().equals("activate")) {
            act = true;
        }
        else if (s.trim().equals("deactivate")) {
            act = false;
        }
        else {
            return status;
        }
        Connection con = AccountDao.getConnection();

        //sql statement that updates an account's status
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE accs SET activated = ? WHERE aid = ?");
            ps.setBoolean(1, act);
            ps.setInt(2, aid);
            status = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int deleteAccount(int aid) {
        int status = 0;
        Connection con = AccountDao.getConnection();

        //sql statement that deletes account based on aid
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM accs WHERE aid = ?");
            ps.setInt(1, aid);
            status = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
