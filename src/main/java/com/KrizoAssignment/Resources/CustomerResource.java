package com.KrizoAssignment.Resources;

import com.KrizoAssignment.Models.Customer;
import com.KrizoAssignment.Services.CustomerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/*
 * The targeted resource when you make an API call to:
 * /customers
 * /customers/<name>
 * /customers/new
 * It produces json objects in the get request and consumes json objects in the post requests
 */

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    // an instance of the customerService to use its methods
    private CustomerService customerService = new CustomerService();

    // this method is invoked when a get request is made to get all the customers is targeted, and it returns a list of customers as a json object
    @GET
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    // this method is invoked when a get request is made to get a list of specific customers that matches the string name,
    // and it returns a list of the targeted customers in a json format
    @GET
    @Path("/{name}")
    public List<Customer> getSpecCustomers(@PathParam("name") String name) {
        return customerService.specificCustomers(name);
    }

    // this method is invoked when a post request is made to add a new customer, it receives a json format of the customer object
    //and then it checks with by invoking the add method from the service class, if a customer can be added or not
    // if a customer is added, a string is returned to the client informing s/he that a customer is added
    // in case a customer is not added, a string is returned to the client to inform s/he that a customer couldn't be added
    @POST
    @Path("/new")
    public String addCustomer(Customer customer) {
        if (customerService.addCustomer(customer))
            return "A new customer is added!";
        else
            return "Failed to add a customer!";
    }

}
