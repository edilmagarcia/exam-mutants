package com.mercadolibre.exam.mutants.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.exam.mutants.dao.DBServiceDAO;
import com.mercadolibre.exam.mutants.detector.MutantDetector;
import com.mercadolibre.exam.mutants.detector.exception.DatabaseException;
import com.mercadolibre.exam.mutants.detector.exception.InputValidationException;
import com.mercadolibre.exam.mutants.detector.exception.ServiceException;
import com.mercadolibre.exam.mutants.detector.impl.MutantDetectorDNABased;
import com.mercadolibre.exam.mutants.model.Human;
import com.mercadolibre.exam.mutants.model.Mutant;
import com.mercadolibre.exam.mutants.service.MutantsService;

@Service
public class MutantsServiceImpl implements MutantsService {
	
	@Autowired	
	private DBServiceDAO dbServiceDAO;
	
	@Override
	public boolean isMutant(String[] dna) throws ServiceException, InputValidationException{
		
		MutantDetector mutantDetector = new MutantDetectorDNABased();
		boolean isMutant = false;
		
		try {
		
			isMutant = mutantDetector.isMutant(dna);
			 
			if(isMutant) {
				Mutant mutant = new Mutant(dna);
				dbServiceDAO.insert(mutant);
			} else {
				Human human = new Human(dna);
				dbServiceDAO.insert(human);
			}
		} catch (DatabaseException ex) {
			throw new ServiceException(ex.getMessage());
		}
		
		return isMutant;
	}

}
