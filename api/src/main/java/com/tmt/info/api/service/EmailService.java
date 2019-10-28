package com.tmt.info.api.service;

import java.util.List;

import com.tmt.info.api.model.EmailDetails;

public interface EmailService {
	
	EmailDetails createEmailDetails(EmailDetails emailDetails);
	List<EmailDetails> getEmailDetails(String providerTin, String corporateTaxID);
}
