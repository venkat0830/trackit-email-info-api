package com.tmt.info.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tmt.info.api.dao.EmailDao;
import com.tmt.info.api.model.BoTable;
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

	@Override
	public Boolean checkUserExists(String providerTin, String uuID) throws Exception {
		boolean isExists =  false;
		try {
			int emailDetailCount =  emailDao.checkEmailDetails(providerTin, uuID);
			if(emailDetailCount > 0) {
				System.out.println("userExists");
				isExists = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}

	@Override
	public EmailDetails createEmailDetails(String providerTin, String uuID, String providerName,
			String primaryEmailAddress, String corporateProviderMpin, BoTable boTable) throws Exception {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setCorporateTaxID(corporateProviderMpin);
		emailDetails.setProviderTin(providerTin);
		emailDetails.setUuID(uuID);
		if ("TRUE".equalsIgnoreCase(boTable.getReconEmailAutoReg())) {
			emailDetails.setReconAlert(true);
			emailDetails.setReconFrequency("Weekly");
			emailDetails.setPrimaryEmailAddress(primaryEmailAddress);
		}
		if ("TRUE".equalsIgnoreCase(boTable.getPendEmailAutoReg())) {
			emailDetails.setPendAlert(true);
			emailDetails.setPendFrequency("Weekly");
			emailDetails.setPrimaryEmailAddress(primaryEmailAddress);
		}
		return emailDetails;
	}
	

}
