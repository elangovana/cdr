package com.ae.assignment.cdrproject.cdrservice;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTalkTime;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTopNCallers;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryRuleConfigDroppedCalls;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryRuleConfigTalkTime;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryRuleConfigTopNCallers;

@Service
public class ServiceRuleConfigTopNCallersPromoImpl implements
		ServiceRuleConfig<RuleConfigTopNCallers> , Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2213375328671724790L;

	private final static Logger LOGGER = Logger
			.getLogger(ServiceRuleConfigTopNCallersPromoImpl.class.getName());

	RepositoryRuleConfigTopNCallers repository ;

	@Autowired
	public void setRepository(
			RepositoryRuleConfigTopNCallers repositoryRuleConfigTopNCallers) {
		this.repository = repositoryRuleConfigTopNCallers;
	}

	static final String ruleName = "TopNCallersPromoRule";

	@Override
	public void Save(RuleConfigTopNCallers rule) {
		rule.ruleName = ruleName;
		rule.lastUpdatedOn = new Date();
		repository.Save(rule);

	}

	@Override
	public RuleConfigTopNCallers Load() {
		RuleConfigTopNCallers result;
		try {
			result = repository.findById(ruleName);
		} catch (javax.persistence.NoResultException ex) {
			LOGGER.log(Level.INFO, String.format(
					"No rule %s found in the database, hence using default",
					ruleName));
			result = new RuleConfigTopNCallers();
			result.isActive = true;
			result.ruleName = ruleName;
			result.promo = "Default Top N Rule";
			result.topNCallers = 10;
			result.calculationPeriodInDays = 10;
		}
		return result;
	}

	@Override
	public RuleConfigTopNCallers GetRuleLastUpdatedSince(
			Date lastUpdatedOn) {
		return repository.GetRuleUpdatedSince(ruleName,
				lastUpdatedOn);
	}

	@Override
	public String getRuleName() {
		return ruleName;
	}
	
	@Override
	public String getRuleTemplateName() {
		return RuleConfigTopNCallers.TEMPLATE_NAME ;
	}

}
