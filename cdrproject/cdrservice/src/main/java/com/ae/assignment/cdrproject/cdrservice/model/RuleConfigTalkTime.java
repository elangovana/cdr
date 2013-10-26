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
@NoSql(dataFormat = DataFormatType.MAPPED, dataType = "ConfigCollection")
public class RuleConfigTalkTime extends RuleConfigBase {

	public static final String TEMPLATE_NAME = "RuleTemplateTalkTime";
	
	public RuleConfigTalkTime()
	{
		setRuleTemplateName(TEMPLATE_NAME);
	}

	@Field(name = "_id")
	@Id
	public String ruleName;

	@Basic
	public String ruleTemplateName;

	@Basic
	public String ruleDescription;

	@Basic
	public String promo;

	@Basic
	public Boolean isActive;

	@Basic
	public int callDurationInMins;

	@Basic
	public int calculationPeriodInDays;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	public Date lastUpdatedOn;

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public int getCallDuration() {
		return callDurationInMins;
	}

	public void setCallDuration(int callDuration) {
		this.callDurationInMins = callDuration;
	}

	public int getCalculationPeriodInDays() {
		return calculationPeriodInDays;
	}

	public void setCalculationPeriodInDays(int calculationPeriodInDays) {
		this.calculationPeriodInDays = calculationPeriodInDays;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Override
	public String getRuleTemplateName() {
		return TEMPLATE_NAME;
	}

	@Override
	protected void setRuleTemplateName(String ruleTemplateName) {
		this.ruleTemplateName = ruleTemplateName;
	}

}
