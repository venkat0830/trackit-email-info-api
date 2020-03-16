package com.tmt.info.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tmt.info.api.model.BoTable;
import com.tmt.info.api.model.EmailDetails;
import com.tmt.info.api.model.NewUser;
import com.tmt.info.api.model.NewUserResponse;
import com.tmt.info.api.model.Status;
import com.tmt.info.api.response.EmailResponse;
import com.tmt.info.api.service.EmailService;
import com.tmt.info.api.utilities.EmailErrorTranslationUtility;
import com.tmt.info.api.utilities.ParameterValidations;

@RestController
@RequestMapping(value = { "/email" })
@SpringBootApplication
public class EmailController {

	@Autowired
	private EmailService emailService;
	@Autowired
	private ParameterValidations parameterValidations;

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

	@RequestMapping(value = "/email/user", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkNewUser(@RequestParam("providerTin") String providerTin,
			@RequestParam("providerName") String providerName, @RequestParam("uuID") String uuID,
			@RequestParam("primaryEmailAddress") String primaryEmailAddress,
			@RequestParam("mpin") String corporateProviderMpin) {
		try {
			List<String> fault;
			fault = parameterValidations.validatereqPara(providerTin, providerName, uuID, primaryEmailAddress,
					corporateProviderMpin);
			if (!fault.isEmpty()) {
				return new ResponseEntity<Object>("BadReq", HttpStatus.BAD_REQUEST);
			}
			Boolean isUserExists = emailService.checkUserExists(providerTin, uuID);
			NewUserResponse newUserResponse = new NewUserResponse();
			NewUser newUser = new NewUser();
			newUserResponse.setNewUser(newUser);
			if (Boolean.TRUE.equals(isUserExists)) {
				newUserResponse.getNewUser().setNewUser(false);
				return new ResponseEntity<Object>(newUserResponse.getNewUser(), HttpStatus.OK);
			}
			System.out.println("User doesnot exists");
			System.out.println("Getting the boTable Details");
			RestTemplate restTemplate = new RestTemplate();
			BoTable boTable = restTemplate.getForObject("www.boTable.com", BoTable.class);
			if (null == boTable) {
				System.out.println("boTable returned null");
				throw new Exception("BoTable returned null");
			}
			EmailDetails emailDetails = emailService.createEmailDetails(providerTin, uuID, providerName,
					primaryEmailAddress, corporateProviderMpin, boTable);
			System.out.println("Calling the saveEmailRecord to save the email details.");
			emailNotificationRequest(emailDetails);
			newUserResponse.getNewUser().setNewUser(true);
			return new ResponseEntity<Object>(newUserResponse.getNewUser(), HttpStatus.OK);
		} catch (Exception ex) {

			NewUserResponse newUserResponse = emailErrorTranslationUtility.generateErrorResp("TITA",
					"Internalservererror", ex.getMessage());
			return new ResponseEntity<Object>(newUserResponse.getErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
