package com.tmt.info.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmt.info.api.model.EmailDetails;
import com.tmt.info.api.model.Status;
import com.tmt.info.api.response.EmailResponse;
import com.tmt.info.api.service.EmailService;
import com.tmt.info.api.utilities.EmailErrorTranslationUtility;

@RestController
@RequestMapping(value = { "/email" })
@SpringBootApplication
public class EmailController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmailErrorTranslationUtility emailErrorTranslationUtility;

	@RequestMapping(value = "/saveEmailDetail", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> emailNotificationRequest(@RequestBody EmailDetails email) {

		EmailResponse emailResponse = emailErrorTranslationUtility.generateEmailResponse(email);
		if (emailResponse != null) {
			return new ResponseEntity<Object>(emailResponse, HttpStatus.BAD_REQUEST);
		}
		emailResponse = new EmailResponse();
		EmailDetails emailDetails = emailService.createEmailDetails(email);
		emailResponse.setEmailDetails(emailDetails);
		Status status = new Status();
		status.setStatus("200Success");
		return new ResponseEntity<Object>(status, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/retrieveEmailDetail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEmailDetails(@RequestParam("providerTin") String providerTin,
			@RequestParam("corporateTaxID") String corporateTaxID) {
		return new ResponseEntity<Object>(emailService.getEmailDetails(providerTin, corporateTaxID), HttpStatus.OK);
		
	}

}
