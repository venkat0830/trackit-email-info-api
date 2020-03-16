package com.tmt.info.api.service;

import java.util.List;

import com.tmt.info.api.model.BoTable;
import com.tmt.info.api.model.EmailDetails;

public interface EmailService {
	
	EmailDetails createEmailDetails(EmailDetails emailDetails);
	List<EmailDetails> getEmailDetails(String providerTin, String corporateTaxID);
	Boolean checkUserExists(String providerTin, String uuID) throws Exception;
	EmailDetails createEmailDetails(String providerTin, String uuID, String providerName, String primaryEmailAddress, String corporateProviderMpin, BoTable boTable) throws Exception;
}
