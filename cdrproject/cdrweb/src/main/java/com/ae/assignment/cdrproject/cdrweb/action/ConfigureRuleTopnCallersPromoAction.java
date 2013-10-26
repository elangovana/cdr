package com.ae.assignment.cdrproject.cdrweb.action;

import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.ae.assignment.cdrproject.cdrservice.ServiceRuleConfig;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTopNCallers;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 */

public class ConfigureRuleTopnCallersPromoAction extends ActionSupport {

	

	private final static Logger LOGGER = Logger
			.getLogger(ConfigureRuleTopnCallersPromoAction.class.getName());

	RuleConfigTopNCallers ruleConfigTopNCallers;

	public RuleConfigTopNCallers getRuleConfigTopNCallers() {
		return ruleConfigTopNCallers;
	}

	public void setRuleConfigTopNCallers(
			RuleConfigTopNCallers ruleConfigTopNCallers) {
		this.ruleConfigTopNCallers = ruleConfigTopNCallers;
	}

	// ServiceRuleConfig<RuleConfigDroppedCalls> service;

	ServiceRuleConfig<RuleConfigTopNCallers> serviceRuleConfigTopNCallersPromoImpl;

	public void setServiceRuleConfigTopNCallersPromoImpl(
			ServiceRuleConfig<RuleConfigTopNCallers> serviceRuleConfigTopNCallersPromoImpl) {
		this.serviceRuleConfigTopNCallersPromoImpl = serviceRuleConfigTopNCallersPromoImpl;
	}

	@Action("configure-rule-topn-callers-promo-view")
	public String execute() throws Exception {
		loadRule();

		return SUCCESS;
	}

	@Action(value = "configure-rule-topn-callers-promo-submit", results = { @Result(name = SUCCESS, location = "configure-rule-update-complete", type = "redirect") })
	public String submitRule() throws Exception {

		serviceRuleConfigTopNCallersPromoImpl
				.Save(ruleConfigTopNCallers);

		return SUCCESS;
	}

	@Action( "configure-rule-topn-callers-promo-edit" )
	public String editRule() throws Exception {

		loadRule();

		return SUCCESS;
	}

	private void loadRule() {
		if (ruleConfigTopNCallers == null)
			ruleConfigTopNCallers = serviceRuleConfigTopNCallersPromoImpl.Load();
	
	}

}
