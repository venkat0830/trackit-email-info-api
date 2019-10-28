package com.tmt.info.api.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.tmt.info.api.model.EmailDetails;
import com.tmt.info.api.response.EmailResponse;
import com.tmt.info.api.response.Error;
import com.tmt.info.api.response.ErrorResponse;

@Component
public class EmailErrorTranslationUtility {

	public static final String ERROR = "Error";
	public static final String APP_ERROR_CODE = "application-error";
	public static final String TITA_1_INFO = "parameter corporateTaxId is Missing"; // corporateTaxId
	public static final String TITA_2_INFO = "parameter providerTin is Missing"; // providerTin
	public static final String TITA_3_INFO = "parameter reconFrequency is Missing"; //reconFrequency
	public static final String TITA_4_INFO = "parameter pendFrequency is Missing";
	public static final String TITA_5_INFO = "parameter reconEmailAddress is either Missing or Incorrect Format"; // reconEmailAddress
	public static final String TITA_6_INFO = "parameter pendEmailAddress is either Missing or Incorrect Format"; // pendEmailAddress
	public static final String TITA_7_INFO = "parameter reconEmailAddress is in incorrect format";
	public static final String TITA_8_INFO = "parameter pendEmailAddress is in incorrect format";

	public EmailResponse generateEmailResponse(EmailDetails email) {
		List<Error> errors = new ArrayList<>();
		EmailResponse emailResponse = new EmailResponse();
		ErrorResponse errorResponse = new ErrorResponse();
		if (StringUtils.isBlank(email.getCorporateTaxID())) {
			Error error = new Error();
			error.setCode(Constants.TITA_1);
			error.setMessage(TITA_1_INFO);
			errors.add(error);
		}
		if (StringUtils.isBlank(email.getProviderTin())) {
			Error error = new Error();
			error.setCode(Constants.TITA_2);
			error.setMessage(TITA_2_INFO);
			errors.add(error);
		}
		if (StringUtils.isBlank(email.getReconFrequency()) && (email.getReconAlert() != null && email.getReconAlert())) {
			Error error = new Error();
			error.setCode(Constants.TITA_3);
			error.setMessage(TITA_3_INFO);
			errors.add(error);
		}
		if (StringUtils.isBlank(email.getPendFrequency()) && (email.getPendAlert() != null && email.getPendAlert())) {
			Error error = new Error();
			error.setCode(Constants.TITA_4);
			error.setMessage(TITA_4_INFO);
			errors.add(error);
		}
		if (isValidEmailId(email.getPendEmailAddress()) && (email.getPendAlert() != null && email.getPendAlert()) ) {
            Error error = new Error();
            error.setCode(Constants.TITA_6);
            error.setMessage(TITA_6_INFO);
            errors.add(error);
        }
    
        if (isValidEmailId(email.getReconEmailAddress()) && (email.getReconAlert() != null && email.getReconAlert())) {
            Error error = new Error();
            error.setCode(Constants.TITA_5);
            error.setMessage(TITA_5_INFO);
            errors.add(error);
        }
//        if (isValidEmailId(email.getReconEmailAddress())&& (email.getReconAlert() != null && email.getReconAlert())) {
//        	   Error error = new Error();
//               error.setCode(Constants.TITA_7);
//               error.setMessage(TITA_7_INFO);
//               errors.add(error);
//        }
//        if (isValidEmailId(email.getPendEmailAddress())  && (email.getPendAlert() != null && email.getPendAlert())) {
//     	   Error error = new Error();
//            error.setCode(Constants.TITA_8);
//            error.setMessage(TITA_8_INFO);
//            errors.add(error);
//     }
		if ((email.getPendAlert() == null && email.getReconAlert() == null)
				|| ((email.getPendAlert() != null && !email.getPendAlert() && email.getReconAlert() != null &&  !email.getReconAlert()))) {
			Error error = new Error();
			error.setCode(Constants.TITA_1);
			error.setMessage(TITA_1_INFO);
			errors.add(error);
		}
		errorResponse.setErrors(errors);
		errorResponse.setStatus(APP_ERROR_CODE);
		errorResponse.setMessage(ERROR);
		emailResponse.setErrorResp(errorResponse);
		if (errors.isEmpty()) {
			return null;
		}
		return emailResponse;
	}

	public boolean isValidEmailId(String email) {
		 String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                 "[a-zA-Z0-9_+&*-]+)*@" + 
                 "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                 "A-Z]{2,7}$"; 
		 Pattern pat = Pattern.compile(emailRegex); 
	        if (email == null) 
	            return false; 
	        return !pat.matcher(email).matches(); 
	    } 
}
