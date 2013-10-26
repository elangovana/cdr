package com.ae.assignment.cdrproject.cdrstream.bolt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.ae.assignment.cdrproject.cdrstream.algorithm.BloomFilterDeduplicator;
import com.ae.assignment.cdrproject.cdrstream.algorithm.IDeduplicate;

public class DuplicateRecordFilterBolt extends BaseRichBolt {

	IDeduplicate<String> duplicateFilter;
	private OutputCollector collector;

	private final static Logger LOGGER = Logger
			.getLogger(DuplicateRecordFilterBolt.class.getName());
	
	static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			CDRFieldNames.DateTimeFormat, Locale.ENGLISH);
	
	public DuplicateRecordFilterBolt(int approximateInsertSize,
			float falsePostiveRate) {
		duplicateFilter = new BloomFilterDeduplicator<String>(
				approximateInsertSize, falsePostiveRate);
	}

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

		String recordString = String.format("%s, %s, %s",
				input.getStringByField(CDRFieldNames.callingNumber),
				input.getStringByField(CDRFieldNames.calledNumber),
				dateFormatter.format( ((Date)input.getValueByField(CDRFieldNames.answerTime))));
		
		boolean isDuplicate = duplicateFilter.Exists(recordString);
		LOGGER.debug(String.format("Checking for duplicate key %s , exists : %s", recordString, isDuplicate));
		if (!isDuplicate) {
			duplicateFilter.Add(recordString);
			Values ouputTuple = new Values();
			ouputTuple.addAll(input.getValues());
			collector.emit(input, ouputTuple);
		}

		collector.ack(input);
	}

}
