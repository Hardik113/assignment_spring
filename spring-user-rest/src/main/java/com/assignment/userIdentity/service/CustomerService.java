package com.assignment.userIdentity.service;

import java.util.List;

import com.assignment.userIdentity.entity.Customer;

public interface CustomerService {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);
	
}
