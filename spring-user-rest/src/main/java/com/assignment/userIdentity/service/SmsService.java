package com.assignment.userIdentity.service;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;


@Service
public class SmsService {
	
	String ACCESS_KEY = "AKIAJWEXOZDKUXU6DCAA";
    String SECRET_KEY = "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";
    
    AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
	
	public String sendSms(String contactNumber, String message) {
		
		return snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(contactNumber)).toString();
		
		
	}
}
