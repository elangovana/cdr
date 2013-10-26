package com.ae.assignment.cdrproject.cdrweb.action;

import java.util.logging.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.ae.assignment.cdrproject.cdrservice.ServiceRuleConfig;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 */

public class ConfigureRuleDroppedCallsPromoAction extends ActionSupport {

	private final static Logger LOGGER = Logger
			.getLogger(ConfigureRuleDroppedCallsPromoAction.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 365626100667253418L;
	RuleConfigDroppedCalls droppedCallsPromoRuleConfig;

	public RuleConfigDroppedCalls getDroppedCallsPromoRuleConfig() {
		return droppedCallsPromoRuleConfig;
	}

	public void setDroppedCallsPromoRuleConfig(
			RuleConfigDroppedCalls droppedCallsPromoRuleConfig) {
		this.droppedCallsPromoRuleConfig = droppedCallsPromoRuleConfig;
	}

	// ServiceRuleConfig<RuleConfigDroppedCalls> service;

	ServiceRuleConfig<RuleConfigDroppedCalls> serviceRuleConfigDroppedCallsPromoImpl;

	public void setServiceRuleConfigDroppedCallsPromoImpl(
			ServiceRuleConfig<RuleConfigDroppedCalls> serviceRuleConfigDroppedCallsPromoImpl) {
		this.serviceRuleConfigDroppedCallsPromoImpl = serviceRuleConfigDroppedCallsPromoImpl;
	}

	@Action("configure-rule-dropped-calls-promo-view")
	public String execute() throws Exception {
		loadRule();

		return SUCCESS;
	}

	@Action(value = "configure-rule-dropped-calls-promo-submit", results = { @Result(name = SUCCESS, location = "configure-rule-update-complete", type = "redirect") })
	public String submitRule() throws Exception {

		serviceRuleConfigDroppedCallsPromoImpl
				.Save(droppedCallsPromoRuleConfig);

		return SUCCESS;
	}

	@Action( "configure-rule-dropped-calls-promo-edit" )
	public String editRule() throws Exception {

		loadRule();

		return SUCCESS;
	}

	private void loadRule() {
		if (droppedCallsPromoRuleConfig == null)
			droppedCallsPromoRuleConfig = serviceRuleConfigDroppedCallsPromoImpl
					.Load();
	}

}
