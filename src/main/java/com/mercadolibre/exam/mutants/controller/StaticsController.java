package com.mercadolibre.exam.mutants.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.mercadolibre.exam.mutants.exception.ServiceException;
import com.mercadolibre.exam.mutants.model.Stats;
import com.mercadolibre.exam.mutants.service.StatsService;

@RestController
public class StaticsController {
	
	@Autowired
	private StatsService statsService;
	
	private static Logger logger =  Logger.getGlobal();
	
	@RequestMapping(value="/stats", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
	public Stats getStatics()  {
		
		Stats stats;
		
		try {
			stats = statsService.getStats();
		} catch (ServiceException e) {
			logger.log(Level.ALL, e.getMessage());
			throw new InternalServerErrorException(e.getMessage());
		}
		
		return stats;
	}
}
