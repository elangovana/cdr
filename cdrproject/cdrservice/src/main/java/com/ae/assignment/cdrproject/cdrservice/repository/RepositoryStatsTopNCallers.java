package com.ae.assignment.cdrproject.cdrservice.repository;

import java.util.Date;

import com.ae.assignment.cdrproject.cdrservice.model.StatsTopNCallers;

public interface RepositoryStatsTopNCallers extends RepositoryGeneric<StatsTopNCallers, String> {
	
	String reportTopCallers(Date fromDate , Date ToDate);
	String reportTopCallers(Date fromDate , Date ToDate, int topN);

}
