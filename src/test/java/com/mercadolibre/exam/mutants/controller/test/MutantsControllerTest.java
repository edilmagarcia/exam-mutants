package com.mercadolibre.exam.mutants.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import com.mercadolibre.exam.mutants.controller.MutantsController;
import com.mercadolibre.exam.mutants.exception.InputValidationException;
import com.mercadolibre.exam.mutants.exception.ServiceException;
import com.mercadolibre.exam.mutants.service.impl.MutantsServiceImpl;

public class MutantsControllerTest {

	private static final String CONTENT_TYPE_JSON = "application/json";

	private static final String URI = "/mutant";
	
	private MockMvc mockMvc;

	@InjectMocks
	private MutantsController controller;

	@Mock
	private MutantsServiceImpl service;

	private String[] dnaMutant =  new String[] {  "ATGCGA", 
												  "CAGTGC", 
												  "TTATGG", 
												  "AGAAGG", 
												  "CCCGTA", 
												  "TCGCTG"};
	
	private String[] dnaHuman =  new String[] {   "ATGCCA", 
												  "CAGTGC", 
												  "TTCTGG", 
												  "AGAAGG", 
												  "CCCGTA", 
												  "TCGCTG"};
	
	private String[] invalidDNA =  new String[] { "ATGCCA", 
												  "CAGTGC", 
												  "TTCTGG", 
												  "AGAAGG", 
												  "TCGCTG"};

	private String mockMutantBody = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGG\",\"AGAAGG\",\"CCCGTA\",\"TCGCTG\"]}";
	private String mockHumanBody = "{\"dna\":[\"ATGCCA\",\"CAGTGC\",\"TTCTGG\",\"AGAAGG\",\"CCCGTA\",\"TCGCTG\"]}";
	private String mockInvalidBody = "{\"dna\":[\"ATGCCA\",\"CAGTGC\",\"TTCTGG\",\"AGAAGG\",\"TCGCTG\"]}";

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.build();
	}
	
	@Test
	public void testAGivenDNAIsRecognizedAsMutant() throws Exception {
		
		// GIVEN a DNA of a mutant subject
		mockIsMutantService(dnaMutant, true);

		// WHEN the mutant endpoint is called
		ResultActions resultActions = mockMvc.perform(post(URI).contentType(CONTENT_TYPE_JSON).content(mockMutantBody));

		// THEN there is an empty response and the status code is 200
		MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		Assert.isTrue(content.isEmpty(), "Response body should be empty");

	}
	
	@Test
	public void testAGivenDNAIsRecognizedAsHuman() throws Exception {
		
		// GIVEN a DNA of a human subject
		mockIsMutantService(dnaHuman, false);
		
		// WHEN the create endpoint is called
		ResultActions resultActions = mockMvc.perform(post(URI).contentType(CONTENT_TYPE_JSON).content(mockHumanBody));
		
		// THEN there is an empty response and the status code is 403
		MvcResult result = resultActions.andExpect(status().isForbidden()).andReturn();
		String content = result.getResponse().getContentAsString();
		Assert.isTrue(content.isEmpty(), "Response body should be empty");
	}
	
	@Test
	public void testAnalizeMutantCandidateIsBadRequest() throws Exception {
		
		// GIVEN a invalid DNA
		Mockito.doThrow(new InputValidationException("any message")).when(service).isMutant(invalidDNA);
		
		// WHEN the create endpoint is called
		ResultActions resultActions = mockMvc.perform(post(URI).contentType(CONTENT_TYPE_JSON).content(mockInvalidBody));
		
		// THEN there is an empty response and the status code is 400
		MvcResult result = resultActions.andExpect(status().isBadRequest()).andReturn();
		String content = result.getResponse().getContentAsString();
		Assert.isTrue(content.isEmpty(), "Response body should be empty");
	}
	
	
	private void mockIsMutantService(String[] dna, boolean expectedResult) throws ServiceException, InputValidationException{
		Mockito.when(service.isMutant(dna)).thenReturn(expectedResult);
	}


}
