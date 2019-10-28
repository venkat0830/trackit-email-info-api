package com.tmt.info.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tmt.info.api.dao.EmailDao;
import com.tmt.info.api.model.EmailDetails;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	EmailDao emailDao;
	
	@Override
	public EmailDetails createEmailDetails(EmailDetails emailDetails) {
		return emailDao.addEmailDetails(emailDetails);
	}
	
	@Override
	public List<EmailDetails> getEmailDetails(String providerTin, String corporateTaxID) {
		return emailDao.getEmailDetails(providerTin, corporateTaxID);
	}
}
