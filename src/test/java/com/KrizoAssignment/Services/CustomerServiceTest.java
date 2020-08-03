package com.KrizoAssignment.Services;

import com.KrizoAssignment.Models.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerServiceTest {

    @Test
    public void getCustomers() {
        assertEquals(true,new CustomerService().getCustomers().size()>0);
    }

    @Test
    public void specificCustomers() {
        assertEquals(true,new CustomerService().specificCustomers("amir").size()>0);
    }

    @Test
    public void addCustomer() {
        Customer c = new Customer();
        c.setEmail("Amir@something.com");
        c.setName("Amir");
        c.setPhone("+4523232323");

        assertEquals(false,new CustomerService().addCustomer(c));
    }
}