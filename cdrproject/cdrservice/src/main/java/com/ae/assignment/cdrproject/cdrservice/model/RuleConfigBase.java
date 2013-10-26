package com.ae.assignment.cdrproject.cdrservice.model;

import java.util.Date;

public abstract class RuleConfigBase {

	public abstract Date getLastUpdatedOn();

	public abstract String getRuleDescription();

	public abstract String getRuleName();

	public abstract String getRuleTemplateName();

	public abstract void setLastUpdatedOn(Date lastUpdatedOn);

	public abstract void setRuleDescription(String ruleDescription);

	public abstract void setRuleName(String ruleName);

	protected abstract void setRuleTemplateName(String ruleTemplateName);

}
