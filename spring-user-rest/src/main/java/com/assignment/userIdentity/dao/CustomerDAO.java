package com.assignment.userIdentity.dao;

import java.util.List;

import com.assignment.userIdentity.entity.Customer;

public interface CustomerDAO {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);
	
}
