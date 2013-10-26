package com.ae.assignment.cdrproject.cdrservice.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

@Entity
@NoSql(dataFormat = DataFormatType.MAPPED, dataType = "TopNCallersCollection")
public class StatsTopNCallers {
	public String getCallingNumber() {
		return callingNumber;
	}


	public void setCallingNumber(String callingNumber) {
		this.callingNumber = callingNumber;
	}


	public int getTotalCallsMade() {
		return totalCallsMade;
	}


	public void setTotalCallsMade(int totalCallsMade) {
		this.totalCallsMade = totalCallsMade;
	}


	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}


	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}


	public String getId() {
		return id;
	}


	@Id
	@GeneratedValue
	@Field(name = "_id")
	String id;

	@Basic
	String callingNumber;
	
	@Basic
	int totalCallsMade;
	

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	Date lastUpdatedOn;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	Date fromDate;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	Date toDate;

	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}


	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
