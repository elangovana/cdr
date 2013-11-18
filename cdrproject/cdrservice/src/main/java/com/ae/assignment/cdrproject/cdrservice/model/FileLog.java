package com.ae.assignment.cdrproject.cdrservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

@Entity
@NoSql(dataFormat = DataFormatType.MAPPED, dataType = "LogsCollection")
public class FileLog implements Serializable {

	@Field(name = "_id")
	@Id
	String fileName;

	@Basic
	int linesProcessed;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	Date lastUpdatedOn;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	Date createdUpdatedOn;

	public Date getCreatedUpdatedOn() {
		return createdUpdatedOn;
	}

	public void setCreatedUpdatedOn(Date createdUpdatedOn) {
		this.createdUpdatedOn = createdUpdatedOn;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLinesProcessed() {
		return linesProcessed;
	}

	public void setLinesProcessed(int linesProcessed) {
		this.linesProcessed = linesProcessed;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
}
