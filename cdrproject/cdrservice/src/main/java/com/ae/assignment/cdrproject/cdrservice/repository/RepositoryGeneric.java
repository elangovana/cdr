package com.ae.assignment.cdrproject.cdrservice.repository;

import java.io.Serializable;



public interface RepositoryGeneric<T extends  Object, indexType> extends Serializable  {
	
	T findById(indexType id);
	
	
	
	T Save(T item);
	
}
