package com.ae.assignment.cdrproject.cdrstream.rulesEvaluator;

import java.io.Serializable;
import java.util.HashMap;

import backtype.storm.tuple.Tuple;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTopNCallers;
import com.ae.assignment.cdrproject.cdrstream.algorithm.ITopKItems;
import com.ae.assignment.cdrproject.cdrstream.bolt.CDRFieldNames;

public class TopNCallersRuleEvaluator implements
		IRuleEvaluator<RuleConfigTopNCallers, HashMap<String, Integer>>,
		Serializable {

	RuleConfigTopNCallers ruleConfig;

	ITopKItems<String> topKItems;

	public TopNCallersRuleEvaluator(RuleConfigTopNCallers ruleConfig) {

		this.ruleConfig = ruleConfig;
	}
	
	public TopNCallersRuleEvaluator() {

		this(null);
	}

	public void setTopKItems(ITopKItems<String> topKItems) {
		this.topKItems = topKItems;
	}

	@Override
	public boolean add(Tuple rec) {
		if (!ruleConfig.isActive)
			return false;

		String callingNumber = rec
				.getStringByField(CDRFieldNames.callingNumber);

		topKItems.add(callingNumber);

		return true;
	}

	@Override
	public HashMap<String, Integer> popCurrentPromos() {
		return topKItems.getCurrentTopItems();
	}

	@Override
	public void setRuleConfig(RuleConfigTopNCallers config) {
		ruleConfig = config;
		topKItems.setTopNCount(ruleConfig.topNCallers);
	}

}
