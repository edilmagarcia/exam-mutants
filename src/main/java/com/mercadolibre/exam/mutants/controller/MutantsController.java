package com.mercadolibre.exam.mutants.controller;

import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.exam.mutants.exception.InputValidationException;
import com.mercadolibre.exam.mutants.exception.ServiceException;
import com.mercadolibre.exam.mutants.model.DNASequence;
import com.mercadolibre.exam.mutants.service.MutantsService;

@RestController
public class MutantsController {
	
	@Autowired
	private MutantsService mutantsService;
	
	private static Logger logger =  Logger.getGlobal();
	
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
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.log(Level.ALL, ex.getMessage());		
		} catch (InputValidationException ex) {
			responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.log(Level.ALL, ex.getMessage());
		}
	
		return responseEntity;
	}
}
