package com.bbva.rbvd.lib.r044;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.elara.domain.transaction.Context;
import com.bbva.elara.domain.transaction.ThreadContext;
import com.bbva.elara.utility.api.connector.APIConnector;
import com.bbva.rbvd.dto.connectionapi.aso.gifole.GifoleInsuranceRequestASO;
import com.bbva.rbvd.lib.r044.impl.RBVDR044Impl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;

import javax.annotation.Resource;

import static com.bbva.rbvd.dto.connectionapi.util.Errors.ERROR_CONNECTION_GIFOLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/RBVDR044-app.xml",
		"classpath:/META-INF/spring/RBVDR044-app-test.xml",
		"classpath:/META-INF/spring/RBVDR044-arc.xml",
		"classpath:/META-INF/spring/RBVDR044-arc-test.xml" })
public class RBVDR044Test {
	private static final Logger LOGGER = LoggerFactory.getLogger(RBVDR044Test.class);

	private Context context;

	private RBVDR044Impl rbvdr044 = new RBVDR044Impl();

	@Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationConfigurationService;

	private APIConnector internalApiConnector;

	private static final String MESSAGE_EXCEPTION = "CONNECTION ERROR";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		context = new Context();
		ThreadContext.set(context);
		
		internalApiConnector = Mockito.mock(APIConnector.class);
		rbvdr044.setInternalApiConnector(internalApiConnector);
	}

	@Test
	public void executeGifoleRegistrationServiceOK() {
		LOGGER.info("RBVDR044Test - Executing executeGifoleServiceOK...");

		when(this.internalApiConnector.exchange(anyString(), any(HttpMethod.class), anyObject(), (Class<Void>) any()))
				.thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

		Integer validation = rbvdr044.executeGifoleRegistration(new GifoleInsuranceRequestASO());

		assertNotNull(validation);
		assertEquals(new Integer(201), validation);
	}

	@Test
	public void executeGifoleRegistrationServiceWithRestClientException() {
		LOGGER.info("RBVDR044Test - Executing executeGifoleServiceWithRestClientException...");

		when(this.internalApiConnector.exchange(anyString(), any(HttpMethod.class), anyObject(), (Class<Void>) any()))
				.thenThrow(new RestClientException(MESSAGE_EXCEPTION));

		Integer validation = rbvdr044.executeGifoleRegistration(new GifoleInsuranceRequestASO());

		assertNull(validation);
		assertEquals(ERROR_CONNECTION_GIFOLE.getAdviceCode(), this.rbvdr044.getAdviceList().get(0).getCode());
	}
	
}
