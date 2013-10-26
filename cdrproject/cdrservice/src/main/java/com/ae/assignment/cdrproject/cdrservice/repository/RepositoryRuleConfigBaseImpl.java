package com.ae.assignment.cdrproject.cdrservice.repository;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Query;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigBase;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;

public abstract class RepositoryRuleConfigBaseImpl <TRule extends RuleConfigBase> extends AbstractRepository<TRule, String> implements  RepositoryRuleConfigBase<TRule> , Serializable{

	
	@Override
	public TRule GetRuleUpdatedSince(String ruleName, Date lastUpdatedOn){

		String queryStr = String
				.format("Select s from %s s where s.lastUpdatedOn > :lastupdatedOn and s.ruleName=:ruleName", getEntityClass().getSimpleName());

		Query query = getEm().createQuery(queryStr);
		query.setParameter("lastupdatedOn", lastUpdatedOn);
		query.setParameter("ruleName", ruleName);
		if (query.getResultList().size() > 0)
			return (TRule) query.getResultList().get(0);

		return null;
	}
}
