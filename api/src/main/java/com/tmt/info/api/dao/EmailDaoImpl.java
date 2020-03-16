package com.tmt.info.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.tmt.info.api.model.EmailDetails;

@Repository
public class EmailDaoImpl implements EmailDao {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public EmailDetails addEmailDetails(EmailDetails emailDetails) {
		
		mongoTemplate.save(emailDetails);

		return emailDetails;
	}
	
	@Override
	public List<EmailDetails> getEmailDetails(String providerTin, String corporateTaxID) {
		Query query = new Query();
		query.addCriteria(Criteria.where("providerTin").is(providerTin));
		query.addCriteria(Criteria.where("corporateTaxID").is(corporateTaxID));
		return mongoTemplate.find(query, EmailDetails.class);
	}

	@Override
	public Integer checkEmailDetails(String providerTin, String uuID) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("uuID").is(uuID));
		return mongoTemplate.find(query, EmailDetails.class).size();
	}

}
