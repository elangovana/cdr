package com.ae.assignment.cdrproject.cdrstream.rulesEvaluator;

import java.io.Serializable;
import java.util.HashMap;

import backtype.storm.tuple.Tuple;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTopNCallers;
import com.ae.assignment.cdrproject.cdrstream.algorithm.IFrequencyCalculator;
import com.ae.assignment.cdrproject.cdrstream.bolt.CDRFieldNames;

public class TopNCallersRuleEvaluator implements
		IRuleEvaluator<RuleConfigTopNCallers, HashMap<String, Integer>>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	RuleConfigTopNCallers ruleConfig;

	IFrequencyCalculator<String> lossyCountAlgorithm;

	public TopNCallersRuleEvaluator(RuleConfigTopNCallers ruleConfig) {

		this.ruleConfig = ruleConfig;
	}
	
	public void setLossyCountAlgorithm(
			IFrequencyCalculator<String> lossyCountAlgorithm) {
		this.lossyCountAlgorithm = lossyCountAlgorithm;
	}

	public TopNCallersRuleEvaluator() {

		this(null);
	}



	@Override
	public boolean add(Tuple rec) {
		if (!ruleConfig.isActive)
			return false;

		String callingNumber = rec
				.getStringByField(CDRFieldNames.callingNumber);

		lossyCountAlgorithm.add(callingNumber);

		return true;
	}

	@Override
	public HashMap<String, Integer> popCurrentPromos() {
		return lossyCountAlgorithm.getCurrentItemsFrequencies();
	}

	@Override
	public void setRuleConfig(RuleConfigTopNCallers config) {
		ruleConfig = config;
		lossyCountAlgorithm.setSupportThreshold(config.getTopCallersPercentage());
	}

}
