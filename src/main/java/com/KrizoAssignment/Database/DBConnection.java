package com.KrizoAssignment.Database;

import com.KrizoAssignment.Models.Customer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// to handle establishing the connection with the database and db operations to get and insert value in the database.
public class DBConnection {

    private Connection connection = null;
    private String url;
    private String user;
    private String dbPassword;


    // the constructor will establish the connection once its instance are invoked by other classes
    public DBConnection() {
        // to read the db credentials in order to log in and connect to the db
        Properties prop = new Properties();
        try (InputStream in = new FileInputStream("/Users/amirsalaheldin/Documents/KriAssignmentAlternative01/db.properties")) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        url = prop.getProperty("database");
        user = prop.getProperty("user");
        dbPassword = prop.getProperty("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    url, user, dbPassword);

            System.out.println("connected");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // to get a list of customers, this method is being used by both get all customers and specifc list of customers
    // this is done by the string query that is passed in the parameter. According to the query a list is produced.
    public List<Customer> getCustomers(String query) {
        List<Customer> customers = new ArrayList<>();

        // to check if the connection, is established
        if (connection != null) {
            // try with resources that disposes after being used, which will close the connection right after being done
            // to avoid sql injections
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    // it will loop thru the results of the query and add the customer object in a list to be returned
                    while (rs.next()) {
                        customers.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3)));
                    }
                }
                // in case of an exception to trace the error
            } catch (SQLException ex) {
                System.out.println("get all customers failed");
                ex.printStackTrace();
                // finally will close the connection anyway, another layer of security. to make sure the connection is closed.
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return customers;
    }

    // this method will add a customer object to the db, it returns true if a customer is added successfully, and false if not.
    public boolean addCustomer(Customer customer) {
        boolean status = false;
        // the inserting query that is going to be used to insert the customer.
        String query = "INSERT INTO customers (customer_name,customer_email,customer_phone) VALUES (?,?,?)";
        //to make sure that connection is established before trying to run queries.
        if (connection != null) {
            status = true;
            // try with resources that disposes after being used, which will close the connection right after being done
            // to avoid sql injections
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setString(1, customer.getName());
                ps.setString(2, customer.getEmail());
                ps.setString(3, customer.getPhone());

                ps.executeUpdate();
                // in case of an exception to trace the error
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("add customer failed.");
                status = false;

                // finally will close the connection anyway, another layer of security. to make sure the connection is closed.
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }

            }

        }
        return status;
    }

    // to check if the email already, exists in the database, in case it exist it will send the count, which will be 1
    // this method, is because the email is the primary key, and an attempt to duplicate PK will cause an error.
    // this way you cannot add customers with the same email, however, no problem adding customers with the same name.
    public int isEmailExist(String email) {
        int count = 0;
        String query = "SELECT count(*) FROM customers WHERE customer_email= ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Query failed.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }


}
