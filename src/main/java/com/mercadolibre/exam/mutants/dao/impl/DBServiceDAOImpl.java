package com.mercadolibre.exam.mutants.dao.impl;

import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mercadolibre.exam.mutants.dao.DBServiceDAO;
import com.mercadolibre.exam.mutants.dao.DynamoDAO;
import com.mercadolibre.exam.mutants.detector.exception.DatabaseException;
import com.mercadolibre.exam.mutants.model.Human;
import com.mercadolibre.exam.mutants.model.Mutant;

/**
 * DynamoDB Implementation of data access layer
 * 
 */
@Service
public class DBServiceDAOImpl extends DynamoDAO implements DBServiceDAO {
	
	public DBServiceDAOImpl() {
		super();
	}
	
	@Override
	public void insert(Mutant mutant)  throws DatabaseException{
		mapper.save(mutant);
	}
	
	@Override
	public void insert(Human human)  throws DatabaseException{
		mapper.save(human);
	}

	@Override
	public int getHumansCount()  throws DatabaseException {
		DynamoDBScanExpression dbScanExpression = new DynamoDBScanExpression();
		return mapper.count(Human.class, dbScanExpression);
	}

	@Override
	public int getMutantsCount()  throws DatabaseException {
		DynamoDBScanExpression dbScanExpression = new DynamoDBScanExpression();
		return mapper.count(Mutant.class, dbScanExpression);
	}
}
