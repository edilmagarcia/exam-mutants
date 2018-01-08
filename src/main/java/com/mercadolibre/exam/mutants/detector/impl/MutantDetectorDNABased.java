package com.mercadolibre.exam.mutants.detector.impl;

import java.util.regex.Pattern;

import com.mercadolibre.exam.mutants.detector.MutantDetector;
import com.mercadolibre.exam.mutants.exception.InputValidationException;

/***
 *  Some performance considerations: When I search for sequences by horizontal and vertical directions, I have a performance
 *  condition to avoid go to the end of the row / column if is not possible to complete a sequence due to the remaining characters
 *  and the number of occurrences.
 *  
 *  Due to lack of time, I did't add the condition in the search by diagonals. This is a pending point to add in a future version
 *  of this algorithm
 *
 */
public class MutantDetectorDNABased implements MutantDetector {
	
	private char[][] dnaSecuence;
	private static final int SECUENCES_TO_CONSIDERER_MUTANT = 2; // Minimum sequences of AAAA, GGGG, TTTT, CCCC to be a mutant.
	private static final int SECUENCES_LENGTH = 4; // The number of characters of the same type to form a sequence.
	private int sequenceCount = 0; // Counter for found sequences
	
	public enum ReadDirections {
	    HORIZONTAL, VERTICAL, // The reading directions for horizontal and vertical matrix scans
	}
	
	public enum DiagonalReadDirections {
		FROM_RIGHT, FROM_LEFT // The direction of scan for diagonals
	}
	
	public enum DiagonalReadType {
		BELOW_MAIN_DIAGONAL, ABOVE_MAIN_DIAGONAL_INCLUDING // If I read below or above (including) of the main diagonal.
	}
	
	/**
	 * This method is used to search sequences in a single row or column depending of a base point and a given direction.
	 * 
	 * @param direction If I read column by column or row by row.
	 * @param startChar The first char read.
	 * @param index The iteration provided to this method in order to scan row by row or column by column.
	 * 
	 * @return true if when this method returns, the number of sequences to be considered a mutant has been reached.
	 * Note: Maybe the number of sequences was reached by this method and a previous one.
	 */
	private boolean readHorizontalOrVertical(ReadDirections direction, char startChar, int index) {
		
		int sequenceMatchCharactersCount = 1; // This counter is used to count the occurrences of the same character. If a complete
											  // sequence or different character is found, this count is reset to 1.
		char lastCharacter = startChar;
		char currentCharacter;
		
		for (int subindex = 1; dnaSecuence.length - subindex  + sequenceMatchCharactersCount >= SECUENCES_LENGTH 
					// The goal of the previous is avoid to scan to the end of the row / column if is not possible
					// to complete a sequence due to the remaining characters and the number of occurrences.
			&&	subindex < dnaSecuence.length; subindex++) {
			
			currentCharacter = (ReadDirections.HORIZONTAL.equals(direction) ? dnaSecuence[index][subindex] : dnaSecuence[subindex][index]);
			if(lastCharacter == currentCharacter) {
				sequenceMatchCharactersCount++;
				if(sequenceMatchCharactersCount == SECUENCES_LENGTH) {
					sequenceCount++;
					sequenceMatchCharactersCount = 0;
					if(sequenceCount == SECUENCES_TO_CONSIDERER_MUTANT) { // If I already find the enough sequences, I finish the search.
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
	
	/***
	 * This method is used to search sequences in a single diagonal depending the base point, direction and if read
	 * above (including) or below the main diagonal. 
	 *
	 * @param leftOrRight The direction of the scan
	 * @param aboveOrBelow If I read the diagonals above the main diagonal (including it) or below the main diagonal.
	 * @param baseN the starting row
	 * @param baseM the starting column
	 * 
	 * @return true if when this method returns, the number of sequences to be considered a mutant has been reached.
	 * Note: Maybe the number of sequences was reached by this method and a previous one.
	 */
	private boolean readDiagonals(DiagonalReadDirections leftOrRight, DiagonalReadType aboveOrBelow, int baseN, int baseM) {
		
		int offset = 1;
		
		int sequenceMatchCharactersCount = 1;	// This counter is used to count the occurrences of the same character. If a complete
												// sequence or different character is found, this count is reset to 1.
		
		char lastCharacter = dnaSecuence[baseN][baseM];
		char currentCharacter;
		
		// This condition is valid when a scan below the main diagonal is still possible.
		boolean bottomReadCondition = baseN + offset < dnaSecuence.length;
				
		// This condition is valid when a scan above the main diagonal (including) is still possible.
		// Note: See that the condition gives true depending the direction of the current scan.
		boolean topReadCondition = (leftOrRight.equals(DiagonalReadDirections.FROM_LEFT) && baseM + offset < dnaSecuence.length ||
				   					leftOrRight.equals(DiagonalReadDirections.FROM_RIGHT)  && baseM - offset >= 0);
		
		// Use the previous conditions to verify that is correct to continue scanning the diagonal.
		while ((aboveOrBelow.equals(DiagonalReadType.ABOVE_MAIN_DIAGONAL_INCLUDING) && topReadCondition) ||
			   (aboveOrBelow.equals(DiagonalReadType.BELOW_MAIN_DIAGONAL) && bottomReadCondition)) {
			
			currentCharacter = (leftOrRight.equals(DiagonalReadDirections.FROM_LEFT)) ? dnaSecuence[baseN + offset][baseM + offset]:
																			            dnaSecuence[baseN + offset][baseM - offset];
			if(lastCharacter == currentCharacter) {
				sequenceMatchCharactersCount++;
				if(sequenceMatchCharactersCount == SECUENCES_LENGTH) {
					sequenceCount++;
					sequenceMatchCharactersCount = 0;
					if(sequenceCount >= SECUENCES_TO_CONSIDERER_MUTANT) { // If I already find the enough sequences, I finish the search.
						return true;
					}	
				}
			} else {
				lastCharacter = currentCharacter;
				sequenceMatchCharactersCount = 1;
			}
			
			offset++;
			
			// I will verify the scanning conditions.
			bottomReadCondition = baseN + offset < dnaSecuence.length;
			
			topReadCondition = (leftOrRight.equals(DiagonalReadDirections.FROM_LEFT) && baseM + offset < dnaSecuence.length ||
					   					   leftOrRight.equals(DiagonalReadDirections.FROM_RIGHT)  && baseM - offset >= 0);
		}
		return false;
	}
	
	/***
	 * Analize if a given dna sequence belongs to a mutant or an human subject.
	 * 
	 * @return true if is a mutant dna sequence or false if is a human sequence.
	 */
	public boolean isMutant(String[] dna) throws InputValidationException{
		
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
			if(readDiagonals(DiagonalReadDirections.FROM_LEFT, DiagonalReadType.BELOW_MAIN_DIAGONAL, row, 0) == true) {
				return true;
			}	
			
		}
		
		// Bottom diagonals, from right, not including main diagonal
		for (int row = 1; row < dnaSecuence.length; row++) {
			lastCharacter = dnaSecuence[row][dnaSecuence.length - 1];
			if(readDiagonals(DiagonalReadDirections.FROM_RIGHT, DiagonalReadType.BELOW_MAIN_DIAGONAL, row, dnaSecuence.length - 1) == true) {
				return true;
			}	
		}
		
		// Top diagonals, from left, including main diagonal
		for (int col = 0; col < dnaSecuence.length; col++) {
			lastCharacter = dnaSecuence[0][col];
			if(readDiagonals(DiagonalReadDirections.FROM_LEFT, DiagonalReadType.ABOVE_MAIN_DIAGONAL_INCLUDING, 0, col) == true) {
				return true;
			}	
			
		}

		// Top diagonals, from right, including main diagonal
		for (int col = 1; col < dnaSecuence.length; col++) {
			lastCharacter = dnaSecuence[0][dnaSecuence.length - col];
			if(readDiagonals(DiagonalReadDirections.FROM_RIGHT,  DiagonalReadType.ABOVE_MAIN_DIAGONAL_INCLUDING,  0, dnaSecuence.length - col) == true) {
				return true;
			}		
		}	
		
		return false;
	}
	
	/**
	 * Populates the two-dimensional array that represents the sequence and check validations while it is done.
	 * 
	 * @param dna the dna sequence
	 * @return a two-dimensional array of chars representing the sequence.
	 * 
	 */
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
					dnaSecuence[range] = dna[range].toUpperCase().toCharArray();
				}
		}
			
		return dnaSecuence;
	}
}
