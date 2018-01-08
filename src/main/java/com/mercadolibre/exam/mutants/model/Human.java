package com.mercadolibre.exam.mutants.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Human")
public class Human {
	
	private String dnaSequence;
	
	public Human(String[] dnaSequence) {
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
