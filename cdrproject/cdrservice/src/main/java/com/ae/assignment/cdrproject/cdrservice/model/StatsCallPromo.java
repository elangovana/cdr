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
@NoSql(dataFormat = DataFormatType.MAPPED, dataType="PromoCollection")
public class StatsCallPromo {
	
	public String getId() {
		return id;
	}

	

	public String getCallingNumber() {
		return callingNumber;
	}

	public void setCallingNumber(String callingNumber) {
		this.callingNumber = callingNumber;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Id
	@GeneratedValue
	@Field(name="_id")
	public String id;
	
	@Basic
	public String callingNumber;
	
	@Basic
	public String rule;
	
	@Basic
	public String promo;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	public Date lastUpdatedOn;
	
	
}
