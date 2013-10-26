package com.ae.assignment.cdrproject.cdrservice;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryRuleConfigDroppedCalls;

@Service

public class ServiceRuleConfigDroppedCallsPromoImpl implements
		ServiceRuleConfig<RuleConfigDroppedCalls> , Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2213375328671724790L;

	private final static Logger LOGGER = Logger
			.getLogger(ServiceRuleConfigDroppedCallsPromoImpl.class.getName());

	RepositoryRuleConfigDroppedCalls droppedCallsRuleRepository;

	@Autowired
	public void setDroppedCallsRuleRepository(
			RepositoryRuleConfigDroppedCalls droppedCallsRuleRepository) {
		this.droppedCallsRuleRepository = droppedCallsRuleRepository;
	}

	public static final String ruleName = "DroppedCallsPromoRule";

	@Override
	public void Save(RuleConfigDroppedCalls rule) {
		rule.setRuleName(ruleName);
		rule.setLastUpdatedOn(new Date());
		droppedCallsRuleRepository.Save(rule);

	}

	@Override
	public RuleConfigDroppedCalls Load() {
		RuleConfigDroppedCalls result;
		try {
			result = droppedCallsRuleRepository.findById(ruleName);
		} catch (javax.persistence.NoResultException ex) {
			LOGGER.log(Level.INFO, String.format(
					"No rule %s found in the database, hence using default",
					ruleName));
			result = new RuleConfigDroppedCalls();
			result.setIsActive(true);
			result.setRuleName(ruleName);
			result.setPromo("Default Dropped Rule");
			result.setNbCallsDropped(10);
			result.setNbCallsDroppedDurationInDays(10);
		}
		return result;
	}

	@Override
	public RuleConfigDroppedCalls GetRuleLastUpdatedSince(
			Date lastUpdatedOn) {
		return droppedCallsRuleRepository.GetRuleUpdatedSince(ruleName,
				lastUpdatedOn);
	}

	@Override
	public String getRuleName() {
		return ruleName;
	}

	@Override
	public String getRuleTemplateName() {
		return RuleConfigDroppedCalls.TEMPLATE_NAME;
	}

}
