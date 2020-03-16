package com.tmt.info.api.dao;

import java.util.List;

import com.tmt.info.api.model.EmailDetails;

public interface EmailDao {

	EmailDetails addEmailDetails(EmailDetails emailDetails);

	List<EmailDetails> getEmailDetails(String providerTin, String corporateTaxID);

	Integer checkEmailDetails(String providerTin, String uuID) throws Exception;
}
