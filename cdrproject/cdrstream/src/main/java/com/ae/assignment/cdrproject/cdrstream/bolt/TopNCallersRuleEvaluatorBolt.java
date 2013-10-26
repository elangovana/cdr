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
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTopNCallers;
import com.ae.assignment.cdrproject.cdrservice.model.StatsCallPromo;
import com.ae.assignment.cdrproject.cdrservice.model.StatsTopNCallers;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryStatsCallPromo;
import com.ae.assignment.cdrproject.cdrservice.repository.RepositoryStatsTopNCallers;
import com.ae.assignment.cdrproject.cdrstream.rulesEvaluator.IRuleEvaluator;

public class TopNCallersRuleEvaluatorBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6270709204043118920L;

	private OutputCollector collector;

	private int emitFrequencyInSeconds;

	HashMap<String, Tuple> map;
	RuleConfigTopNCallers ruleConfig;

	ServiceRuleConfig<RuleConfigTopNCallers> ruleConfigService;

	IRuleEvaluator<RuleConfigTopNCallers, HashMap<String, Integer>> ruleEvaluator;

	RepositoryStatsCallPromo repository;
	
	RepositoryStatsTopNCallers repositoryTopNCaller;

	public void setRepositoryTopNCaller(
			RepositoryStatsTopNCallers repositoryTopNCaller) {
		this.repositoryTopNCaller = repositoryTopNCaller;
	}

	private final static Logger LOGGER = Logger
			.getLogger(TopNCallersRuleEvaluatorBolt.class.getName());

	static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			CDRFieldNames.DateTimeFormat, Locale.ENGLISH);

	public TopNCallersRuleEvaluatorBolt(int emitFrequencyInSec) {
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

	private void peristPromos() {
		HashMap<String, Integer> hashMap = ruleEvaluator.popCurrentPromos();
		for (Iterator<Entry<String, Integer>> subIterator = hashMap.entrySet()
				.iterator(); subIterator.hasNext();) {

			Entry<String, Integer> entry = subIterator.next();
			// Save the promo

			StatsCallPromo dbo = new StatsCallPromo();
			dbo.setCallingNumber(entry.getKey());
			dbo.setRule(ruleConfigService.getRuleName());
			dbo.setPromo(ruleConfig.getPromo());
			dbo.setLastUpdatedOn(new Date());
			repository.Save(dbo);
			
			StatsTopNCallers dboCallers = new StatsTopNCallers();
			dboCallers.setTotalCallsMade(entry.getValue());
			dboCallers.setCallingNumber(entry.getKey());;
			dboCallers.setLastUpdatedOn(new Date());
			repositoryTopNCaller.Save(dboCallers);

			subIterator.remove();

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
			if (RuleConfigTopNCallers.class.isInstance(inputTuple)) {

				ruleConfig = (RuleConfigTopNCallers) inputTuple;
				ruleEvaluator.setRuleConfig(ruleConfig);
			}

		}

		else {

			ruleEvaluator.add(input);

		}

		collector.ack(input);
	}

	public void setRepository(RepositoryStatsCallPromo repository) {
		this.repository = repository;
	}

	public void setRuleConfigService(
			ServiceRuleConfig<RuleConfigTopNCallers> ruleConfigService) {
		this.ruleConfigService = ruleConfigService;
	}

	public void setRuleEvaluator(
			IRuleEvaluator<RuleConfigTopNCallers, HashMap<String, Integer>> ruleEvaluator) {
		this.ruleEvaluator = ruleEvaluator;
	}

}
