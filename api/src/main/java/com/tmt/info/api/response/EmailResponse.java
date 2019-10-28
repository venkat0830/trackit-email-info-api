package com.tmt.info.api.response;

import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmt.info.api.model.EmailDetails;

public class EmailResponse {
	
	@JsonIgnore
	EmailDetails emailDetails;
	String status;
	ErrorResponse errorResp;
	
	MultiValueMap<String, String> headerMap;

	public EmailDetails getEmailDetails() {
		return emailDetails;
	}

	public void setEmailDetails(EmailDetails emailDetails) {
		this.emailDetails = emailDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ErrorResponse getErrorResp() {
		return errorResp;
	}

	public void setErrorResp(ErrorResponse errorResp) {
		this.errorResp = errorResp;
	}

	public MultiValueMap<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(MultiValueMap<String, String> headerMap) {
		this.headerMap = headerMap;
	}
	
	

}
