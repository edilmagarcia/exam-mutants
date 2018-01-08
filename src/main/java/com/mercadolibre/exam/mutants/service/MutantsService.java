package com.mercadolibre.exam.mutants.service;

import com.mercadolibre.exam.mutants.detector.exception.InputValidationException;
import com.mercadolibre.exam.mutants.detector.exception.ServiceException;

public interface MutantsService {
	
	public boolean isMutant(String[] dna) throws ServiceException, InputValidationException;

}
