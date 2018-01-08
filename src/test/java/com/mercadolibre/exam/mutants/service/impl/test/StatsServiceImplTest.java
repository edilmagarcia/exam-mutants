package com.mercadolibre.exam.mutants.service.impl.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercadolibre.exam.mutants.dao.impl.DBServiceDAOImpl;
import com.mercadolibre.exam.mutants.exception.DatabaseException;
import com.mercadolibre.exam.mutants.exception.ServiceException;
import com.mercadolibre.exam.mutants.model.Stats;
import com.mercadolibre.exam.mutants.service.impl.StatsServiceImpl;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class StatsServiceImplTest {
	

	@InjectMocks
	private StatsServiceImpl service;
	
	@Mock
	private DBServiceDAOImpl dao;	
	
	@Test
	public void testStatsRetrieveStatsSuccessfully() throws ServiceException, DatabaseException {
		
		// GIVEN a count from humans and mutants
		Mockito.when(dao.getMutantsCount()).thenReturn(1);
		Mockito.when(dao.getHumansCount()).thenReturn(10);

		// WHEN the service is called for stats
		Stats stats = service.getStats();

		// THEN a stats object is returned
		TestCase.assertNotNull(stats);
	}
}