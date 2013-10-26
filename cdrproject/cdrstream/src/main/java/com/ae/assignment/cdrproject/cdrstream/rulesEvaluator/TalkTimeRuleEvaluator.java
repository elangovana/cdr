package com.ae.assignment.cdrproject.cdrstream.rulesEvaluator;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import backtype.storm.tuple.Tuple;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTalkTime;
import com.ae.assignment.cdrproject.cdrstream.bolt.CDRFieldNames;
import com.ae.assignment.cdrproject.cdrstream.bolt.DuplicateRecordFilterBolt;

public class TalkTimeRuleEvaluator implements
		IRuleEvaluator<RuleConfigTalkTime,HashMap<String, HashMap<Integer, RulesResultPerCaller<Integer>>> > {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2990201078832426112L;

	public HashMap<String, HashMap<Integer, RulesResultPerCaller<Integer>>> map;

	private RuleConfigTalkTime config;

	private final static Logger LOGGER = Logger
			.getLogger(TalkTimeRuleEvaluator.class.getName());
	
	public TalkTimeRuleEvaluator(RuleConfigTalkTime config) {
		this.config = config;
		map = new HashMap<String, HashMap<Integer, RulesResultPerCaller<Integer>>>();
	}

	public TalkTimeRuleEvaluator() {
		this(null);
	}

	@Override
	public boolean add(Tuple rec) {
		if (!config.getIsActive())
			return false;

		Date releaseTime = (Date) rec.getValueByField(CDRFieldNames.releaseTime);
		String callingNumber = rec.getStringByField(CDRFieldNames.callingNumber);
		float callDuration  = rec.getFloatByField(CDRFieldNames.callDuration);
		int periodIndex = DatesSlicer.GetYearlyIndex(releaseTime,
				config.getCalculationPeriodInDays());

		HashMap<Integer, RulesResultPerCaller<Integer>> subMap = map.get(callingNumber);
		// Create Sub Map for the calling Number
		if (subMap == null) {
			subMap = new HashMap<Integer, RulesResultPerCaller<Integer>>();
			map.put(callingNumber, subMap);

		}
		// Create periodIndex and corresponding Resultant Tuples
		RulesResultPerCaller<Integer> rulesResult = subMap.get(periodIndex);
		if (rulesResult == null) {
			rulesResult = new RulesResultPerCaller<Integer>();
			rulesResult.calcSummary=0;
			subMap.put(periodIndex, rulesResult);
		}
		// Add tuples for ack.
		rulesResult.tuplesToAck.add(rec);
		LOGGER.debug(String.format("Caculating callduration %s", callDuration));
		rulesResult.calcSummary += (int) Math.floor(callDuration);
		
		// Set promo
		if (rulesResult.calcSummary >= config.getCallDurationInMins()) {
			rulesResult.promo = config.getPromo();
		}

		return true;
	}

	@Override
	public void setRuleConfig(RuleConfigTalkTime config) {
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
