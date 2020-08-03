package com.KrizoAssignment.Database;

import com.KrizoAssignment.Models.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBConnectionTest {

    @Test
    public void getCustomers() {

        assertSame(true,new DBConnection().getCustomers("SELECT * FROM customers").size()>0);
    }

    @Test
    public void addCustomer() {
        Customer c = new Customer();
        c.setEmail("something@something.com");
        c.setName("Jimmy");
        c.setPhone("+4523232323");

        assertEquals(true,new DBConnection().addCustomer(c));
    }

    @Test
    public void isEmailExist() {

        assertEquals(1,new DBConnection().isEmailExist("amir@email.com"));

    }
}