package com.mercadolibre.exam.mutants.restcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.mercadolibre.exam.mutants.detector.exceptions.ServiceException;
import com.mercadolibre.exam.mutants.pojos.DNASequence;

public interface MutantsController {
	
	public ResponseEntity<String> analizeMutantCandidate(@RequestBody DNASequence dna) throws ServiceException;

}
