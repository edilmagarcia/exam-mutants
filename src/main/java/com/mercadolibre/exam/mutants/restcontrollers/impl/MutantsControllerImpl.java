package com.mercadolibre.exam.mutants.restcontrollers.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.exam.mutants.detector.exceptions.ServiceException;
import com.mercadolibre.exam.mutants.pojos.DNASequence;
import com.mercadolibre.exam.mutants.restcontrollers.MutantsController;
import com.mercadolibre.exam.mutants.service.MutantsService;


@RestController
public class MutantsControllerImpl implements MutantsController {
	
	@Autowired
	MutantsService mutantsService;
	
	@RequestMapping(value="/mutant", method=RequestMethod.POST)
	public ResponseEntity<String> analizeMutantCandidate(@RequestBody DNASequence dna) {
		
		ResponseEntity<String> responseEntity = null;
		boolean isMutant;	

		try {
			isMutant = mutantsService.isMutant(dna.getDna());
			
			if(isMutant) {
				responseEntity = new ResponseEntity<>(HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			
		} catch (ServiceException ex) {
			Logger  logger = Logger.getGlobal();
			logger.info(ex.getMessage());
		}
	
		return responseEntity;
	}
	
	@ExceptionHandler({ ServiceException.class })
    public void handleException(ServiceException ex) {
		Logger  logger = Logger.getGlobal();
		logger.info(ex.getMessage());
    }

}
