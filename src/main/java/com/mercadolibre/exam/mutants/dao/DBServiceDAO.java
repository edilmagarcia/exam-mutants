package com.mercadolibre.exam.mutants.dao;

import com.mercadolibre.exam.mutants.detector.exceptions.DatabaseException;
import com.mercadolibre.exam.mutants.pojos.Human;
import com.mercadolibre.exam.mutants.pojos.Mutant;

public interface DBServiceDAO {
	
	public void insert(Mutant mutant) throws DatabaseException;
	
	public void insert(Human human) throws DatabaseException;
	
	public int getHumansCount() throws DatabaseException;
	
	public int getMutantsCount() throws DatabaseException;
}
