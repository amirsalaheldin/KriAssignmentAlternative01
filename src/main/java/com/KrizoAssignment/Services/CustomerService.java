package com.KrizoAssignment.Services;

import com.KrizoAssignment.Database.DBConnection;
import com.KrizoAssignment.Models.Customer;

import java.util.List;

// this class will be the services class, which will consume the dbconnection class in order to serve the customer resource
// this to organise and make the code easily read and understood.
public class CustomerService {


    public CustomerService() {
    }

    // it will return a list of customers
    public List<Customer> getCustomers() {
        return new DBConnection().getCustomers("SELECT * FROM customers");
    }

    // it will return a list of specific customers
    public List<Customer> specificCustomers(String name) {
        return new DBConnection().getCustomers("SELECT * FROM customers where customer_name ='" + name + "'");
    }

    // to add a customer, first it will check if the customer's email already exists, in case it does not, it will try to add it
    // if it does exist, then it will return false.
    public boolean addCustomer(Customer customer) {
        if (new DBConnection().isEmailExist(customer.getEmail()) == 0) {
            return new DBConnection().addCustomer(customer);
        } else {
            return false;
        }
    }


}
