package com.mercadolibre.exam.mutants.service;

import com.mercadolibre.exam.mutants.exception.ServiceException;
import com.mercadolibre.exam.mutants.model.Stats;

public interface StatsService {
	
	public Stats getStats() throws ServiceException;

}
