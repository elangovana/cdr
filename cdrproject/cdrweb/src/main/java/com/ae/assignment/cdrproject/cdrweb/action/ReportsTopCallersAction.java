package com.ae.assignment.cdrproject.cdrweb.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryStatsTopNCallers;
import com.opensymphony.xwork2.ActionSupport;

public class ReportsTopCallersAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Calendar calender =  Calendar.getInstance();

	


	RepositoryStatsTopNCallers repositoryStatsTopNCallersImpl;

	public ReportsTopCallersAction() {
	
	}

	public void setRepositoryStatsTopNCallersImpl(
			RepositoryStatsTopNCallers repositoryStatsTopNCallersImpl) {
		this.repositoryStatsTopNCallersImpl = repositoryStatsTopNCallersImpl;
	}

	String latestStatsTopCallers;

	public InputStream getJsonOutput()
	{
		return new ByteArrayInputStream(latestStatsTopCallers.getBytes());
	}

	public String execute() throws Exception {

		
		return SUCCESS;
	}
	

	public String retrieveReportData() throws Exception {

		latestStatsTopCallers = repositoryStatsTopNCallersImpl
				.reportTopCallers(currentDate(),  currentDate(),10);
	
		return SUCCESS;
	}

	Date currentDate()
	{
		calender.setTime(new Date());
		calender.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		return calender.getTime();
	}
	
}
