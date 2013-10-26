package com.ae.assignment.cdrproject.cdrservice.repository;

import java.util.Date;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigBase;

public interface RepositoryRuleConfigBase<TRule extends RuleConfigBase> extends
		RepositoryGeneric<TRule, String> {

	TRule GetRuleUpdatedSince(String ruleName, Date lastUpdatedOn);
}
