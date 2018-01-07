package com.mercadolibre.exam.mutants.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.exam.mutants.dao.DBServiceDAO;
import com.mercadolibre.exam.mutants.detector.exceptions.DatabaseException;
import com.mercadolibre.exam.mutants.detector.exceptions.ServiceException;
import com.mercadolibre.exam.mutants.pojos.Stats;
import com.mercadolibre.exam.mutants.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService{
	
	@Autowired
	DBServiceDAO dbServiceDAO;
	
	@Override
	public Stats getStats() throws ServiceException{
		
		Stats stats;
		
		try {
			stats = new Stats(dbServiceDAO.getMutantsCount(), dbServiceDAO.getHumansCount());
		} catch (DatabaseException ex) {
			throw new ServiceException(ex.getMessage());
		}
		
		return stats;
	}

}
