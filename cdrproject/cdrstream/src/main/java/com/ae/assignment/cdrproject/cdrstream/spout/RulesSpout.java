package com.ae.assignment.cdrproject.cdrstream.spout;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import com.ae.assignment.cdrproject.cdrservice.ServiceRuleConfigDroppedCallsPromoImpl;
import com.ae.assignment.cdrproject.cdrservice.ServiceRuleConfig;

public class RulesSpout extends BaseRichSpout {

	 List<ServiceRuleConfig<?>> rulesList;

	Date lastUpdatedOn;

	SpoutOutputCollector collector;

	int pollIntervalInMinutes;

	public RulesSpout(int pollIntervalInMinutes) {
		this.pollIntervalInMinutes = pollIntervalInMinutes;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("rule"));
	}

	private Object getRule(ServiceRuleConfig<?> ruleService) {
		Object rule;
		if (lastUpdatedOn != null) {
			rule = ruleService.GetRuleLastUpdatedSince(lastUpdatedOn);
		} else {
			rule = ruleService.Load();
		}
		return rule;
	}

	public List<ServiceRuleConfig<?>> getRulesList() {
		return rulesList;
	}

	@Override
	public void nextTuple() {
		Date initTimeStamp = new Date();
		for (Iterator<ServiceRuleConfig<?>> iterator = rulesList.iterator(); iterator
				.hasNext();) {
			ServiceRuleConfig<?> ruleService = iterator.next();
			Object rule = getRule(ruleService);

			if (rule != null)
				collector.emit(new Values(rule));

		}
		lastUpdatedOn = initTimeStamp;
		Utils.sleep(pollIntervalInMinutes * 60 * 1000);
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;

	}

	public void setRulesList(List<ServiceRuleConfig<?>> rulesList) {
		this.rulesList = rulesList;
	}

}
