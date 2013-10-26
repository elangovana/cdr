
package com.ae.assignment.cdrproject.cdrweb.action;

import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.ae.assignment.cdrproject.cdrservice.ServiceRuleConfig;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTalkTime;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 */

public class ConfigureRuleTalkTimePromoAction extends ActionSupport {

	private final static Logger LOGGER = Logger
			.getLogger(ConfigureRuleDroppedCallsPromoAction.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 365626100667253418L;
	
	RuleConfigTalkTime ruleConfigTalkTime;

	

	//ServiceRuleConfig<RuleConfigDroppedCalls> service;
	
	ServiceRuleConfig<RuleConfigTalkTime> serviceRuleConfigTalkTimePromoImpl;

	@Action( "configure-rule-talk-time-promo-edit" )
	public String editRule() throws Exception {

		loadRule();

		return SUCCESS;
	}

	@Action( "configure-rule-talk-time-promo-view")
	public String execute() throws Exception {
		loadRule();
	
		return SUCCESS;
	}
	
	

	public RuleConfigTalkTime getRuleConfigTalkTime() {
		return ruleConfigTalkTime;
	}

	private void loadRule() {
		if (ruleConfigTalkTime == null)
			ruleConfigTalkTime = serviceRuleConfigTalkTimePromoImpl.Load();
	}

	public void setRuleConfigTalkTime(RuleConfigTalkTime ruleConfigTalkTime) {
		this.ruleConfigTalkTime = ruleConfigTalkTime;
	}
	
	public void setserviceRuleConfigTalkTimePromoImpl(
			ServiceRuleConfig<RuleConfigTalkTime> serviceRuleConfigTalkTimePromoImpl) {
		this.serviceRuleConfigTalkTimePromoImpl = serviceRuleConfigTalkTimePromoImpl;
	}

	@Action(value = "configure-rule-talk-time-promo-submit", results = { @Result(name = SUCCESS, location = "configure-rule-update-complete", type = "redirect") })
	public String submitRule() throws Exception {

		serviceRuleConfigTalkTimePromoImpl.Save(ruleConfigTalkTime);

		return SUCCESS;
	}
	
}
