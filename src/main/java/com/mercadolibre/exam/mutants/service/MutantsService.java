package com.mercadolibre.exam.mutants.service;

import com.mercadolibre.exam.mutants.detector.exceptions.ServiceException;

public interface MutantsService {
	
	public boolean isMutant(String[] dna) throws ServiceException;

}
