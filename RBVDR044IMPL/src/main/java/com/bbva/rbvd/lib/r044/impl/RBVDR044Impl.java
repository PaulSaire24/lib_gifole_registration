package com.bbva.rbvd.lib.r044.impl;

import com.bbva.rbvd.dto.internalapi.aso.gifole.GifoleInsuranceRequestASO;
import com.bbva.rbvd.dto.internalapi.util.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.nio.charset.StandardCharsets;

import static com.bbva.rbvd.dto.internalapi.util.ApiUriKeys.GIFOLE;
import static com.bbva.rbvd.dto.internalapi.util.Constants.APPLICATION;
import static com.bbva.rbvd.dto.internalapi.util.Constants.JSON;
import static com.bbva.rbvd.dto.internalapi.util.Errors.ERROR_CONNECTION_GIFOLE;
import static org.springframework.http.HttpMethod.POST;

public class RBVDR044Impl extends RBVDR044Abstract {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RBVDR044Impl.class);

	@Override
	public Integer executeGifoleRegistration(GifoleInsuranceRequestASO requestBody) {
		LOGGER.info("***** RBVDR044Impl - executeGifoleRegistration START *****");
		String jsonString = getRequestJson(requestBody);
		LOGGER.info("***** RBVDR044Impl - executeGifoleRegistration ***** Request body: {}", jsonString);
		Integer httpStatus = null;
		HttpEntity<String> entity = new HttpEntity<>(jsonString, createHttpHeaders());
		try {
			ResponseEntity<Void> response = this.internalApiConnector.exchange(GIFOLE.getConsoleKey(), POST, entity, Void.class);
			httpStatus = response.getStatusCode().value();
			LOGGER.info("***** RBVDR044Impl - executeGifoleRegistration ***** Http code response: {}", httpStatus);
		} catch(RestClientException ex) {
			LOGGER.debug("***** RBVDR044Impl - executeGifoleRegistration ***** Exception: {}", ex.getMessage());
			this.addAdvice(ERROR_CONNECTION_GIFOLE.getAdviceCode());
		}
		LOGGER.info("***** RBVDR044Impl - executeGifoleRegistration END *****");
		return httpStatus;
	}

	private String getRequestJson(Object o) {
		return JsonHelper.getInstance().serialization(o);
	}

	private HttpHeaders createHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(APPLICATION,JSON, StandardCharsets.UTF_8);
		headers.setContentType(mediaType);
		return headers;
	}

}
