package com.tmt.info.api.model;

import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmt.info.api.response.ErrorResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewUserResponse {
	
	@JsonIgnore
	NewUser newUser;
	String status;
	ErrorResponse errorResponse;
	MultiValueMap<String, String> headerMap;
	public NewUser getNewUser() {
		return newUser;
	}
	public void setNewUser(NewUser newUser) {
		this.newUser = newUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
	public MultiValueMap<String, String> getHeaderMap() {
		return headerMap;
	}
	public void setHeaderMap(MultiValueMap<String, String> headerMap) {
		this.headerMap = headerMap;
	}
	

}
