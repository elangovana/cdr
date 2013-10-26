package com.ae.assignment.cdrproject.cdrstream.rulesEvaluator;

import java.util.ArrayList;
import java.util.List;

import backtype.storm.tuple.Tuple;

public class RulesResultPerCaller<TCalculationSummmary> {
	
	public RulesResultPerCaller() {
		tuplesToAck = new ArrayList<Tuple>();
	}

	public List<Tuple> tuplesToAck;
	public String promo;
	public TCalculationSummmary calcSummary;

}