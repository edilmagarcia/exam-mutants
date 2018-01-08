package com.mercadolibre.exam.mutants.service.impl.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercadolibre.exam.mutants.dao.impl.DBServiceDAOImpl;
import com.mercadolibre.exam.mutants.exception.DatabaseException;
import com.mercadolibre.exam.mutants.exception.InputValidationException;
import com.mercadolibre.exam.mutants.exception.ServiceException;
import com.mercadolibre.exam.mutants.model.Human;
import com.mercadolibre.exam.mutants.model.Mutant;
import com.mercadolibre.exam.mutants.service.impl.MutantsServiceImpl;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class MutantsServiceImplTest {
	
	private String[] dnaMutant1 =  new String[] { 
			  "ATGCGA", 
			  "CAGTGC", 
			  "TTATGG", 
			  "AGAAGG", 
			  "CCCCTA", 
			  "TCGCTG"};
	
	private String[] dnaMutant2 =  new String[] { 
			  "GTGCGA", 
			  "CACTGC", 
			  "TCATGG", 
			  "CGATAG", 
			  "CCCTTA", 
			  "TCCCCG"};
	
	private String[] dnaHuman1 =  new String[] { 
			  "GTGCGA", 
			  "CACTAC", 
			  "TCCTGG", 
			  "CGAAAG", 
			  "CCCGTA", 
			  "TCGCTG"};
	
	private String[] dnaHuman2 =  new String[] { 
			  "GTGCGA", 
			  "CACTGC", 
			  "TCATGG", 
			  "CGAAAG", 
			  "CCCGTA", 
			  "TCGCTG"};

	@InjectMocks
	private MutantsServiceImpl service;
	
	@Mock
	private DBServiceDAOImpl dao;	
	
	@Test
	public void testIsMutant_Success_Mutant1() throws DatabaseException, ServiceException, InputValidationException {

		// GIVEN a dna of a mutant subject
		String[] dna = dnaMutant1;
		Mutant mutant = new Mutant(dna);
		Mockito.doNothing().when(dao).insert(Mockito.eq(mutant));

		// WHEN is mutant service is executed
		boolean result = service.isMutant(dna);

		// THEN the return is true
		TestCase.assertTrue(result);
	}
	
	@Test
	public void testIsMutant_Success_Mutant2() throws DatabaseException, ServiceException, InputValidationException {
		
		// GIVEN a dna of a mutant subject
		String[] dna = dnaMutant2;
		Mutant mutant = new Mutant(dna);
		Mockito.doNothing().when(dao).insert(Mockito.eq(mutant));

		// WHEN is mutant service is executed
		boolean result = service.isMutant(dna);

		// THEN the return is true
		TestCase.assertTrue(result);
	}
	
	@Test
	public void testIsMutant_Error_Human1() throws DatabaseException, ServiceException, InputValidationException {
		// GIVEN a dna of a human subject
		String[] dna = dnaHuman1;
		Human h = new Human(dna);
		Mockito.doNothing().when(dao).insert(Mockito.eq(h));

		// WHEN is mutant service is executed
		boolean result = service.isMutant(dna);

		// THEN the return is false
		TestCase.assertFalse(result);
	}
	
	@Test
	public void testIsMutant_Error_Human2() throws DatabaseException, ServiceException, InputValidationException {
		// GIVEN a dna of a human subject
		String[] dna = dnaHuman2;
		Human h = new Human(dna);
		Mockito.doNothing().when(dao).insert(Mockito.eq(h));

		// WHEN is mutant service is executed
		boolean result = service.isMutant(dna);

		// THEN the return is false
		TestCase.assertFalse(result);
	}
	
}