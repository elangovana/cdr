package com.ae.assignment.cdrproject.cdrstream.bolt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class DroppedCallsFilterBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1614609222243471832L;
	private OutputCollector collector;

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(CDRFieldNames.getFieldsCDRRecord());

	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;

	}

	@Override
	public void execute(Tuple input) {

		String causeForTermination = input
				.getStringByField(CDRFieldNames.causeForTermination);
		if (causeForTermination.equals(CDRFieldNames.DroppedCallCode)) {
			Values ouputTuple = new Values();
			ouputTuple.addAll(input.getValues());
			collector.emit(input, ouputTuple);
		}
		collector.ack(input);
	}

}
