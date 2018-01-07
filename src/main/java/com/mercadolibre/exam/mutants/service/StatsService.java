package com.mercadolibre.exam.mutants.service;

import com.mercadolibre.exam.mutants.detector.exceptions.ServiceException;
import com.mercadolibre.exam.mutants.pojos.Stats;

public interface StatsService {
	
	public Stats getStats() throws ServiceException;

}
