package com.ae.assignment.cdrproject.cdrstream.rulesEvaluator;

import java.io.Serializable;
import java.util.HashMap;

import backtype.storm.tuple.Tuple;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;

public interface IRuleEvaluator<TConfig, TResult> extends Serializable {

	/** 
	 * Return false if the tuple is not processed, for instance when the rule is inactive
	 * @param rec
	 * @return
	 */
	boolean add(Tuple rec);

	TResult popCurrentPromos();

	void setRuleConfig(TConfig config);

}
