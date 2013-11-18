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
@NoSql(dataFormat = DataFormatType.MAPPED, dataType = "ConfigCollection")
public class RuleConfigDroppedCalls extends RuleConfigBase implements Serializable{

	public static final String TEMPLATE_NAME = "RuleTemplateDroppedCalls";

	@Field(name = "_id")
	@Id
	String ruleName;

	@Basic
	protected String ruleTemplateName;

	@Basic
	String ruleDescription;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	Date lastUpdatedOn;

	public RuleConfigDroppedCalls() {
		setRuleTemplateName(TEMPLATE_NAME);
	}

	@Basic
	String promo;

	@Basic
	private
	Boolean isActive;

	@Basic
	int nbCallsDropped;

	@Basic
	int nbCallsDroppedDurationInDays;

	public Boolean getIsActive() {
		return isActive;
	}

	public int getNbCallsDropped() {
		return nbCallsDropped;
	}

	public int getNbCallsDroppedDurationInDays() {
		return nbCallsDroppedDurationInDays;
	}

	public String getPromo() {
		return promo;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setNbCallsDropped(int nbCallsDropped) {
		this.nbCallsDropped = nbCallsDropped;
	}

	public void setNbCallsDroppedDurationInDays(int nbCallsDroppedDurationInDays) {
		this.nbCallsDroppedDurationInDays = nbCallsDroppedDurationInDays;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	@Override
	public String getRuleDescription() {
		return ruleDescription;
	}

	@Override
	public String getRuleName() {
		return ruleName;
	}

	@Override
	public String getRuleTemplateName() {
		return ruleTemplateName;
	}

	@Override
	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Override
	public Date getLastUpdatedOn() {
		return lastUpdatedOn;

	}

	@Override
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}

	@Override
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Override
	protected void setRuleTemplateName(String ruleTemplateName) {
		this.ruleTemplateName = ruleTemplateName;

	}

}
