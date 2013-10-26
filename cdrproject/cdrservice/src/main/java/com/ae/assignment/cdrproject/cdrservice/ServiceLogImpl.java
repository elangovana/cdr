package com.ae.assignment.cdrproject.cdrservice;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ae.assignment.cdrproject.cdrservice.model.FileLog;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryFileLog;

@Service

public class ServiceLogImpl implements ServiceLog , Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2749878805123298216L;
	RepositoryFileLog repository;
	private final static Logger LOGGER = Logger
			.getLogger(ServiceLogImpl.class.getName());
	
	@Autowired
	public void setRepository(RepositoryFileLog repository) {
		this.repository = repository;
	}

	@Override
	public int retrieveLinesRead(String fileName)
	{
		FileLog dbo ;
		int result = 0;
		try {
			dbo = repository.findById(fileName);
			result = dbo.getLinesProcessed();
			
		} catch (javax.persistence.NoResultException ex) {
			LOGGER.log(Level.INFO, String.format(
					"No file %s found in the database, hence using default",
					fileName));
		}
		return result;
	}
	
	
	@Override
	public void putLinesRead(String fileName, int linesReadSoFar)
	{
		FileLog dbo = new FileLog();
		dbo.setFileName(fileName);
		dbo.setLinesProcessed(linesReadSoFar) ;
		repository.Save(dbo);
		
	}

}
