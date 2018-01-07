package com.mercadolibre.exam.mutants.utils;

import java.util.Random;

public class MutantsUtils {
	
	public static String[] generateRandomDNASequence() {
		
		String dnaSequence[] = new String[2];
		String alphabet = "ACGT";
		Random rand = new Random(); 
		int dnaLength = rand.nextInt();
		
		for (int rows = 0; rows < dnaLength; rows++) {
			
			for (int nitrobase = 0; nitrobase < dnaLength; nitrobase++) {
				char charIndex = alphabet.charAt(rand.nextInt(alphabet.length()));
			}
		}
		
		return dnaSequence;
	}
}
