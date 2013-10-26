package com.ae.assignment.cdrproject.cdrservice.model;

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
@NoSql(dataFormat = DataFormatType.MAPPED, dataType="LogsCollection")
public class FileLog {
	
	@Field(name="_id")
	@Id
	public String fileName;
	
	@Basic
	public int linesProcessed;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	public Date lastUpdatedOn;
}
