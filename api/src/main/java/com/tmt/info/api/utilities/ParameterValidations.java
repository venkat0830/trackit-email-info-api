package com.tmt.info.api.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ParameterValidations {

	public List<String> validatereqPara(String providerTin, String providerName, String uuID,
			String primaryEmailAddress, String corporateProviderMpin) {
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(providerTin)) {
			errors.add("providerTin should not be empty");
		}
		if (StringUtils.isBlank(uuID)) {
			errors.add("uuID should not be empty");
		}
		return errors;
	}

}
