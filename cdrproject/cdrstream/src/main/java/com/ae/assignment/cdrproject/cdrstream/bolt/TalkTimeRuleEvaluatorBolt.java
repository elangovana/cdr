package com.ae.assignment.cdrproject.cdrstream.bolt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import backtype.storm.Config;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import com.ae.assignment.cdrproject.cdrservice.ServiceRuleConfig;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTalkTime;
import com.ae.assignment.cdrproject.cdrservice.model.StatsCallPromo;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryStatsCallPromo;
import com.ae.assignment.cdrproject.cdrstream.rulesEvaluator.IRuleEvaluator;
import com.ae.assignment.cdrproject.cdrstream.rulesEvaluator.RulesResultPerCaller;

public class TalkTimeRuleEvaluatorBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6270709204043118920L;

	private OutputCollector collector;

	private int emitFrequencyInSeconds;

	HashMap<String, Tuple> map;
	RuleConfigTalkTime ruleConfig;

	ServiceRuleConfig<RuleConfigTalkTime> ruleConfigService;

	IRuleEvaluator<RuleConfigTalkTime, HashMap<String, HashMap<Integer, RulesResultPerCaller<Integer>>>> ruleEvaluator;

	RepositoryStatsCallPromo repository;

	private final static Logger LOGGER = Logger
			.getLogger(TalkTimeRuleEvaluatorBolt.class.getName());

	static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			CDRFieldNames.DateTimeFormat, Locale.ENGLISH);
	
	
	public TalkTimeRuleEvaluatorBolt(int emitFrequencyInSec) {
		this.emitFrequencyInSeconds = emitFrequencyInSec;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(CDRFieldNames.getFieldsCDRRecord());
	}

	@Override
	public void execute(Tuple input) {

		if (TupleHelper.isTickTuple(input)) {
			// Persist Promo
			peristPromos();
		} else {
			processTuple(input);
		}
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Config conf = new Config();

		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, emitFrequencyInSeconds);
		return conf;

	}

	private void peristAndAckTuples(
			Entry<String, HashMap<Integer, RulesResultPerCaller<Integer>>> mapEntry) {
		for (Iterator<Entry<Integer, RulesResultPerCaller<Integer>>> subIterator = mapEntry
				.getValue().entrySet().iterator(); subIterator.hasNext();) {

			Entry<Integer, RulesResultPerCaller<Integer>> subMapEntry = subIterator
					.next();
			// Save the promo
			if (subMapEntry.getValue().promo != null) {
				StatsCallPromo dbo = new StatsCallPromo();
				dbo.callingNumber = mapEntry.getKey();
				dbo.rule = ruleConfigService.getRuleName();
				dbo.promo = subMapEntry.getValue().promo;
				dbo.lastUpdatedOn = new Date();
				repository.Save(dbo);

			}
			// Ack the tuples
			for (int i = 0; i < subMapEntry.getValue().tuplesToAck.size(); i++) {
				collector.ack(subMapEntry.getValue().tuplesToAck.get(i));

			}

			subIterator.remove();

		}
	}

	private void peristPromos() {
		HashMap<String, HashMap<Integer, RulesResultPerCaller<Integer>>> map = ruleEvaluator
				.popCurrentPromos();
		for (Iterator<Entry<String, HashMap<Integer, RulesResultPerCaller<Integer>>>> iterator = map
				.entrySet().iterator(); iterator.hasNext();) {

			Entry<String, HashMap<Integer, RulesResultPerCaller<Integer>>> mapEntry = iterator
					.next();

			peristAndAckTuples(mapEntry);
			iterator.remove();

		}
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;

		ruleConfig = ruleConfigService.Load();
		ruleEvaluator.setRuleConfig(ruleConfig);
	}

	private void processTuple(Tuple input) {
		Object inputTuple = input.getValue(0);
		LOGGER.debug(String.format("Source Stream Id %s",
				input.getSourceComponent()));
		// If rule then update Rule

		if (input.getSourceComponent().equals(TopologyConstants.StreamIDRules)) {
			// Only pick up dropped call promo changes.
			if (RuleConfigTalkTime.class.isInstance(inputTuple)) {
				ruleEvaluator
						.setRuleConfig((RuleConfigTalkTime) inputTuple);
			}
			collector.ack(input);
		}

		else {
			// CDR Record then, evaluate rule
			if (!ruleEvaluator.add(input)) {
				// ack the tuple immediately as the rule is not active
				collector.ack(input);
			}

		}
	}

	public void setRepository(RepositoryStatsCallPromo repository) {
		this.repository = repository;
	}

	public void setRuleConfigService(
			ServiceRuleConfig<RuleConfigTalkTime> ruleConfigService) {
		this.ruleConfigService = ruleConfigService;
	}
	
	public void setRuleEvaluator(
			IRuleEvaluator<RuleConfigTalkTime, HashMap<String, HashMap<Integer, RulesResultPerCaller<Integer>>>> ruleEvaluator) {
		this.ruleEvaluator = ruleEvaluator;
	}

}
