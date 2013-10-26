package com.ae.assignment.cdrproject.cdrstream.rulesEvaluator;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import backtype.storm.tuple.Tuple;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;
import com.ae.assignment.cdrproject.cdrstream.bolt.CDRFieldNames;

public class DroppedCallsRuleEvaluator implements
		IRuleEvaluator<RuleConfigDroppedCalls, HashMap<String, HashMap<Integer, RulesResultPerCaller<Integer>>> > {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4352124309098457776L;

	public HashMap<String, HashMap<Integer,RulesResultPerCaller<Integer>>> map;
	
	

	private RuleConfigDroppedCalls config;

	public DroppedCallsRuleEvaluator(RuleConfigDroppedCalls config) {
		this.config = config;
		map = new HashMap<String, HashMap<Integer,RulesResultPerCaller<Integer>>>();
	}

	public DroppedCallsRuleEvaluator() {
		this(null);
	}

	@Override
	public boolean add(Tuple rec) {
		if (!config.getIsActive())
			return false;

		Date seizureTime = (Date) rec
				.getValueByField(CDRFieldNames.releaseTime);
		String callingNumber = rec
				.getStringByField(CDRFieldNames.callingNumber);

		int periodIndex = DatesSlicer.GetYearlyIndex(seizureTime,
				config.getNbCallsDroppedDurationInDays());

		HashMap<Integer, RulesResultPerCaller<Integer>> subMap = map.get(callingNumber);
		// Create Sub Map for the calling Number
		if (subMap == null) {
			subMap = new HashMap<Integer, RulesResultPerCaller<Integer>>();
			map.put(callingNumber, subMap);

		}
		// Create periodIndex and corresponding Resultant TuplesI
		RulesResultPerCaller<Integer> rulesResult = subMap.get(periodIndex);
		if (rulesResult == null) {
			rulesResult = new RulesResultPerCaller<Integer>();
			rulesResult.calcSummary=0;
			subMap.put(periodIndex, rulesResult);
		}
		// Add tuples for ack.
		rulesResult.tuplesToAck.add(rec);
		// Set promo
		if (rulesResult.tuplesToAck.size() >= config.getNbCallsDropped()) {
			rulesResult.promo = config.getPromo();
		}

		return true;
	}

	@Override
	public void setRuleConfig(RuleConfigDroppedCalls config) {
		this.config = config;
	}

	Date getDatePart(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// Set time fields to zero
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		date = cal.getTime();
		return date;
	}

	@Override
	public HashMap<String, HashMap<Integer, RulesResultPerCaller<Integer>>> popCurrentPromos() {
		return map;
	}

	

}
