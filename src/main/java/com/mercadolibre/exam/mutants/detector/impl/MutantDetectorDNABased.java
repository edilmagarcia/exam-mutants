package com.mercadolibre.exam.mutants.detector.impl;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.mercadolibre.exam.mutants.detector.MutantDetector;
import com.mercadolibre.exam.mutants.detector.exceptions.InputValidationException;

public class MutantDetectorDNABased implements MutantDetector {
	
	private char[][] dnaSecuence;
	private static final int SECUENCES_TO_CONSIDERER_MUTANT = 2;
	private static final int SECUENCES_LENGTH = 4;
	private int sequenceCount = 0;
	
	public enum ReadDirections {
	    HORIZONTAL, VERTICAL, 
	}
	
	public enum DiagonalReadDirections {
		FROM_RIGHT, FROM_LEFT
	}
	
	public enum DiagonalReadType {
		BOTTOM, TOP_INCLUDING_MAIN_DIAG
	}
	
    public static void main( String[] args )
    {
    	MutantDetectorDNABased  detectorDNABased  = new MutantDetectorDNABased();
    	
    	String[] dnaMutant1 =  new String[] { "tTGCGA", 
		    								  "CAGTcC", 
		    								  "TTATGG", 
		    								  "AGAAGG", 
		    								  "CCCGTA", 
		    								  "TCGCTG"};
    	
    	String[] dnaMutant2 =  new String[] { "GTGCGA", 
											  "CACTGC", 
											  "TCATGG", 
											  "CGAAAG", 
											  "CCCGTA", 
											  "TCGCTG"};
    
    	boolean isMutant = detectorDNABased.isMutant(dnaMutant1);
    	
    	if(isMutant) 
			System.out.println("ES MUTANTE");
		else
			System.out.println("ES HUMANO");
    	
    }
	
	private boolean readHorizontalOrVertical(ReadDirections direction, char startChar, int index) {
		
		int sequenceMatchCharactersCount = 1;
		char lastCharacter = startChar;
		char currentCharacter;
		
		for (int subindex = 1; subindex < dnaSecuence.length && 
				dnaSecuence.length - subindex  + sequenceMatchCharactersCount >= SECUENCES_LENGTH; subindex++) {
			
			currentCharacter = (ReadDirections.HORIZONTAL.equals(direction) ? dnaSecuence[index][subindex] : dnaSecuence[subindex][index]);
			if(lastCharacter == currentCharacter) {
				sequenceMatchCharactersCount++;
				if(sequenceMatchCharactersCount == SECUENCES_LENGTH) {
					sequenceCount++;
					sequenceMatchCharactersCount = 0;
					if(sequenceCount > 1) {
						return true;
					}	
				}
			} else {
				lastCharacter = currentCharacter;
				sequenceMatchCharactersCount = 1;
			}
		}
		
		return false;
	}
	
	private boolean readDiagonals(DiagonalReadDirections leftOrRight, DiagonalReadType topOrBottom, char startChar, int index) {
		
		int baseN;
		int baseM;
		int offset = 1;
		
		if(topOrBottom.equals(DiagonalReadType.BOTTOM)) {
			baseN = index;
			baseM = (leftOrRight.equals(DiagonalReadDirections.FROM_LEFT)) ? 0 : dnaSecuence.length - 1;
		} else {
			baseN = 0;
			baseM = index;
		}
		
		int sequenceMatchCharactersCount = 1;
		char lastCharacter = startChar;
		char currentCharacter;
		
		boolean bottomReadCondition = baseN + offset < dnaSecuence.length;
				
		boolean topReadCondition = (leftOrRight.equals(DiagonalReadDirections.FROM_LEFT) && baseM + offset < dnaSecuence.length ||
				   					   leftOrRight.equals(DiagonalReadDirections.FROM_RIGHT)  && baseM - offset >= 0);
				   					  
		while ((topOrBottom.equals(DiagonalReadType.TOP_INCLUDING_MAIN_DIAG) && topReadCondition) ||
			   (topOrBottom.equals(DiagonalReadType.BOTTOM) && bottomReadCondition)) {
			
			currentCharacter = (leftOrRight.equals(DiagonalReadDirections.FROM_LEFT)) ? dnaSecuence[baseN + offset][baseM + offset]:
																			    dnaSecuence[baseN + offset][baseM - offset];
			if(lastCharacter == currentCharacter) {
				sequenceMatchCharactersCount++;
				if(sequenceMatchCharactersCount == SECUENCES_LENGTH) {
					sequenceCount++;
					sequenceMatchCharactersCount = 0;
					if(sequenceCount >= SECUENCES_TO_CONSIDERER_MUTANT) {
						return true;
					}	
				}
			} else {
				lastCharacter = currentCharacter;
				sequenceMatchCharactersCount = 1;
			}
			
			offset++;
			
			bottomReadCondition = baseN + offset < dnaSecuence.length;
			
			topReadCondition = (leftOrRight.equals(DiagonalReadDirections.FROM_LEFT) && baseM + offset < dnaSecuence.length ||
					   					   leftOrRight.equals(DiagonalReadDirections.FROM_RIGHT)  && baseM - offset >= 0);
		}
		return false;
	}
			
	public boolean isMutant(String[] dna) {
		
		try {
			char lastCharacter;
			
			dnaSecuence = populateDnaSecuence(dna);
			
			if(dnaSecuence.length < 4) {
				return false;
			}
			
			// Horizontal read
			for (int row = 0; row < dnaSecuence.length; row++) {
				lastCharacter = dnaSecuence[row][0];
				if(readHorizontalOrVertical(ReadDirections.HORIZONTAL, lastCharacter, row) == true) {
					return true;
				}	
			}
			
			// Vertical read
			for (int col = 0; col < dnaSecuence.length; col++) {
				lastCharacter = dnaSecuence[0][col];
				if(readHorizontalOrVertical(ReadDirections.VERTICAL, lastCharacter, col) == true) {
					return true;
				}
			}

			// Bottom diagonals, from left, not including main diagonal
			for (int row = 1; row < dnaSecuence.length; row++) {
				lastCharacter = dnaSecuence[row][0];
				if(readDiagonals(DiagonalReadDirections.FROM_LEFT, DiagonalReadType.BOTTOM, lastCharacter, row) == true) {
					return true;
				}	
				
			}
			
			// Bottom diagonals, from right, not including main diagonal
			for (int row = 1; row < dnaSecuence.length; row++) {
				lastCharacter = dnaSecuence[row][dnaSecuence.length - 1];
				if(readDiagonals(DiagonalReadDirections.FROM_RIGHT, DiagonalReadType.BOTTOM, lastCharacter, row) == true) {
					return true;
				}	
			}
			
			// Top diagonals, from left, including main diagonal
			for (int col = 0; col < dnaSecuence.length; col++) {
				lastCharacter = dnaSecuence[0][col];
				if(readDiagonals(DiagonalReadDirections.FROM_LEFT, DiagonalReadType.TOP_INCLUDING_MAIN_DIAG, lastCharacter, col) == true) {
					return true;
				}	
				
			}

			// Top diagonals, from right, including main diagonal
			for (int col = 1; col < dnaSecuence.length; col++) {
				lastCharacter = dnaSecuence[0][dnaSecuence.length - col];
				if(readDiagonals(DiagonalReadDirections.FROM_RIGHT,  DiagonalReadType.TOP_INCLUDING_MAIN_DIAG, lastCharacter, col) == true) {
					return true;
				}		
			}	
		} catch (InputValidationException ex) {
			Logger  logger = Logger.getGlobal();
			logger.info(ex.getMessage());
		}
		
		return false;
	}
	   
	private char[][] populateDnaSecuence(String[] dna) throws InputValidationException{
		
		int dnaSecuenceRange = dna.length;
		Pattern pattern = Pattern.compile("[atcg]+", Pattern.CASE_INSENSITIVE);
		
		dnaSecuence = new char[dnaSecuenceRange][dnaSecuenceRange];
		
		for (int range = 0; range < dnaSecuenceRange ; range++) {
			
				if(dna[range].length() != dnaSecuenceRange) {
					throw new InputValidationException("The length of every DNA secuences has to be equal to the number of secuences");
				} else if(!pattern.matcher(dna[range]).matches()){
					throw new InputValidationException("The characters provided in the secuence are invalid. The only valid characters are A, T, G and C.");
				} else {
					dnaSecuence[range] = dna[range].toCharArray();
				}
		}
			
		return dnaSecuence;
	}
}
