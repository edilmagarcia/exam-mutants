package com.mercadolibre.exam.mutants.model;

public class Stats {
	
	private int count_mutant_dna;
	private int count_human_dna;
	private double ratio;
	
	public Stats(int mutantCount, int humanCount) {
		count_mutant_dna = mutantCount;
		count_human_dna = humanCount;
		ratio = (count_human_dna != 0) ? count_mutant_dna / count_human_dna : 0;
	}
	
	public int getCount_mutant_dna() {
		return count_mutant_dna;
	}
	public void setCount_mutant_dna(int count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}
	public int getCount_human_dna() {
		return count_human_dna;
	}
	public void setCount_human_dna(int count_human_dna) {
		this.count_human_dna = count_human_dna;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	
	
}
