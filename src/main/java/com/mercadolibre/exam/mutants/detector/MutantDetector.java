package com.mercadolibre.exam.mutants.detector;

public interface MutantDetector {
	
	/**
	 * Evaluates if a subject is a mutant
	 * 
	 * @param The mutant information to be evaluated in order to check if the subject is mutant or not 
	 * @return True if the subject is mutant or false if the subject is not.
	 */
	boolean isMutant(String[] param);
}
