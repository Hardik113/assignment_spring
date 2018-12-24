package com.assignment.userIdentity.rest;

import java.security.Key;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.userIdentity.entity.Customer;
import com.assignment.userIdentity.service.CustomerService;
import com.assignment.userIdentity.service.SmsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	SmsService smsService;
	
	@PostMapping("/signup")
	public Customer signUp(@RequestBody Customer customer) {
		
		String thisOtp;
	
		thisOtp = String.format("%04d", new Random().nextInt(10000));
		
		customer.setStatus("unverified");
		customer.setOtp(thisOtp);
		
		smsService.sendSms(thisOtp, customer.getContactNumber());
		
		customerService.saveCustomer(customer);
		
		return customer;
	}
	
	@PostMapping("/verifyOtp")
	@ResponseBody
	public ResponseEntity verifyOtp(@RequestParam("otp") String otp, @RequestParam("id") int id) {
		
		Customer customer = customerService.getCustomer(id);
		
		if (customer == null) {
			
			return new ResponseEntity("Customer id does not exist", HttpStatus.NOT_FOUND);
		}
		
		if (otp.equals(customer.getOtp())) {
			customer.setStatus("verified");
			
			customerService.saveCustomer(customer);
			
			return new ResponseEntity("token:" + generateToken(customer), HttpStatus.ACCEPTED);
		} else {
			
			return new ResponseEntity("Otp does not match", HttpStatus.NOT_FOUND);
			
		}
	}
	
	private String generateToken(Customer customer) {
		
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
		String jws = Jwts.builder().setSubject(customer.toString()).signWith(key).compact();
		
		return jws;
		
	}

}
