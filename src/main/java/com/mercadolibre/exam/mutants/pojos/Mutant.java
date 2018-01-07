package com.mercadolibre.exam.mutants.pojos;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Mutant")
public class Mutant {
	
	private String dnaSequence;
	
	public Mutant(String[] dnaSequence) {
		this.dnaSequence = String.join("", dnaSequence);
	}
	
	@DynamoDBHashKey(attributeName="dnaSequence")
	public String getDnaSequence() {
		return dnaSequence;
	}

	public void setDnaSequence(String dnaSequence) {
		this.dnaSequence = dnaSequence;
	}
}
