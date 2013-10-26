package com.ae.assignment.cdrproject.cdrweb.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryStatsCallPromo;
import com.opensymphony.xwork2.ActionSupport;

public class ReportsPromoCallsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	RepositoryStatsCallPromo repositoryStatsCallPromoImpl;

	public ReportsPromoCallsAction() {
	
	}

	public void setRepositoryStatsCallPromoImpl(
			RepositoryStatsCallPromo repositoryStatsCallPromoImpl) {
		this.repositoryStatsCallPromoImpl = repositoryStatsCallPromoImpl;
	}

	String latestStatsPromo;

	public InputStream getJsonOutput()
	{
		return new ByteArrayInputStream(latestStatsPromo.getBytes());
	}

	public String execute() throws Exception {

		
		return SUCCESS;
	}
	

	public String retrieveReportData() throws Exception {

		latestStatsPromo = repositoryStatsCallPromoImpl
				.reportPromoCount(5);
		return SUCCESS;
	}

	
	
}
