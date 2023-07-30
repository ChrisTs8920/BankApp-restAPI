# Bank Application - Java RESTful API

## Description

An application of remote management of bank accounts. It was made using Java, Jakarta RESTful web services (JAX-RS API, Jersey implementation), MySQL (and JDBC API), IntelliJ IDEA Ultimate and Glassfish 6.2.4.  

**Tested using Mozilla Firefox and Postman.**

*This project was made during my Network programming course in University.*

### The application offers the following actions

1. Create account
2. Deposit amount
3. Withdraw amount
4. Transfer to another account
5. Enable/Disable account
6. View details of an account (XML/JSON)
7. View details of all accounts (XML/JSON)
8. Delete account

**For simplicity, we assume that the user can execute the above actions to any account simply by knowing the Account ID. (i.e without requiring any kind of authentication).**

### Endpoints

0. ```http://localhost:8080/BankApp-1.0-SNAPSHOT/``` ```//main page```

1. ```http://localhost:8080/BankApp-1.0-SNAPSHOT/addAccount.html```

2. ```http://localhost:8080/BankApp-1.0-SNAPSHOT/api/bankService/deposit/<aid>?amount=<amount>```

3. ```http://localhost:8080/BankApp-1.0-SNAPSHOT/api/bankService/withdraw/<aid>?amount=<amount>```

4. ```http://localhost:8080/BankApp-1.0-SNAPSHOT/api/bankService/transferBalance/<aid>?aid2=<aid>&amount=<amount>```

5. ```http://localhost:8080/BankApp-1.0-SNAPSHOT/api/bankService/updateAccountStatus/<aid>/<status>```

6. ```http://localhost:8080/BankApp-1.0-SNAPSHOT/api/bankService/allAccounts_json/<aid>```
   ```http://localhost:8080/BankApp-1.0-SNAPSHOT/api/bankService/allAccounts_xml/<aid>```

7. ```http://localhost:8080/BankApp-1.0-SNAPSHOT/api/bankService/allAccounts_json```
   ```http://localhost:8080/BankApp-1.0-SNAPSHOT/api/bankService/allAccounts_xml```

8. ```http://localhost:8080/BankApp-1.0-SNAPSHOT/api/bankService/delete/<aid>```

*Where `<aid>` insert the appropriate account id, and `<amount>` insert the appropriate amount.*

*`<status>` is either activate or deactivate.*

### Database

The sql file creates the database **bank_accs** which contains the following table:

| aid | name | surname | contactPhone | address | accBalance | activated |
|-----|------|---------|--------------|---------|------------|-----------|
| 10 | CHRIS | TSOUCHLAKIS | 6986868969 | Street 42 | 100 | 1 |
| 11 | MARKOS | MARKOY | 6985368422 | Street 47 | 225 | 1 |
| 12 | IWANNA | IWANNOY | 6976663345 | Street 107 | 150 | 1 |
| 13 | IWANNHS | IWANNOY | 6977775569 | Street 107 | 10 | 1 |
| 14 | GIWRGOS | GIWRGOY | 6966665544 | Street 21 | 1000 | 1 |

**The database also creates a user named 'TSOUCHLAKIS' with password '1234', with all privileges granted. This user is required for the JDBC API to connect with the database.**

## Requirements

1. Glassfish (tested on version 6.2.4) or Tomcat
2. MySQL installation
3. JDBC API (included)
4. JAX-RS API (included, also comes with Glassfish)
