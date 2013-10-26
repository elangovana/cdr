package com.ae.assignment.cdrproject.cdrservice;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTalkTime;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryRuleConfigDroppedCalls;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryRuleConfigTalkTime;

@Service
public class ServiceRuleConfigTalkTimePromoImpl implements
		ServiceRuleConfig<RuleConfigTalkTime> , Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2213375328671724790L;

	private final static Logger LOGGER = Logger
			.getLogger(ServiceRuleConfigTalkTimePromoImpl.class.getName());

	RepositoryRuleConfigTalkTime repository ;

	@Autowired
	public void setRepository(
			RepositoryRuleConfigTalkTime repositoryRuleConfigTalkTime) {
		this.repository = repositoryRuleConfigTalkTime;
	}

	static final String ruleName = "TalkTimePromoRule";

	@Override
	public void Save(RuleConfigTalkTime rule) {
		rule.setRuleName ( ruleName);
		rule.setLastUpdatedOn(new Date());
		repository.Save(rule);

	}

	@Override
	public RuleConfigTalkTime Load() {
		RuleConfigTalkTime result;
		try {
			result = repository.findById(ruleName);
		} catch (javax.persistence.NoResultException ex) {
			LOGGER.log(Level.INFO, String.format(
					"No rule %s found in the database, hence using default",
					ruleName));
			result = new RuleConfigTalkTime();
			result.setIsActive (true);
			result.setRuleName(ruleName);
			result.setPromo("Default Talk Time Rule");
			result.setCallDurationInMins(10);
			result.setCalculationPeriodInDays(10);
		}
		return result;
	}

	@Override
	public RuleConfigTalkTime GetRuleLastUpdatedSince(
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
		return RuleConfigTalkTime.TEMPLATE_NAME ;
	}

}
