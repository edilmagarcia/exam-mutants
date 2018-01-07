package com.mercadolibre.exam.mutants.restcontrollers.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.exam.mutants.detector.exceptions.ServiceException;
import com.mercadolibre.exam.mutants.pojos.Stats;
import com.mercadolibre.exam.mutants.restcontrollers.StaticsController;
import com.mercadolibre.exam.mutants.service.StatsService;

@RestController
public class StaticsControllerImpl implements StaticsController {
	
	@Autowired
	StatsService statsService;
	
	@RequestMapping(value="/stats", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
	public Stats getStatics() {
			
		Stats stats = null;
		try {
			stats = statsService.getStats();
		} catch (ServiceException ex) {
			Logger  logger = Logger.getGlobal();
			logger.info(ex.getMessage());
		}
		
		return stats;
	}
	
	@ExceptionHandler({ ServiceException.class })
    public void handleException(ServiceException ex) {
		Logger  logger = Logger.getGlobal();
		logger.info(ex.getMessage());
    }
}
