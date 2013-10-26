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
public class RuleConfigTopNCallers extends RuleConfigBase {

	public static final String TEMPLATE_NAME = "RuleTemplateTopNCallers";

	public RuleConfigTopNCallers() {
		setRuleTemplateName(TEMPLATE_NAME);
	}

	@Field(name = "_id")
	@Id
	String ruleName;

	@Basic
	String ruleDescription;

	@Basic
	String ruleTemplateName;

	@Basic
	String promo;

	@Basic
	Boolean isActive;

	@Basic
	float topCallersPercentage;

	@Basic
	int calculationPeriodInDays;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	Date lastUpdatedOn;

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

	public float getTopCallersPercentage() {
		return topCallersPercentage;
	}

	public void setTopCallersPercentage(float topCallersPercentage) {
		this.topCallersPercentage = topCallersPercentage;
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
	public String getRuleName() {
		return this.ruleName;
	}

	@Override
	public String getRuleTemplateName() {
		return TEMPLATE_NAME;
	}

	@Override
	public void setRuleName(String ruleName) {
		this.ruleTemplateName = ruleName;
	}

	@Override
	protected void setRuleTemplateName(String ruleTemplateName) {
		this.ruleTemplateName = ruleTemplateName;
	}

}
