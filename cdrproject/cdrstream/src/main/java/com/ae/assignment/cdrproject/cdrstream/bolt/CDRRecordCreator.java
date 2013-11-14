package com.ae.assignment.cdrproject.cdrstream.bolt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class CDRRecordCreator extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9077267746223186460L;

	private final static Logger LOGGER = Logger
			.getLogger(CDRRecordCreator.class.getName());

	OutputCollector collector;

	final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			CDRFieldNames.DateTimeFormat, Locale.ENGLISH);

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(CDRFieldNames.getFieldsCDRRecord()

		);

	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {

		String[] items = input.getString(0).split(",");
		Values values = new Values();

		for (int i = 0; i < items.length; i++) {
			items[i] = items[i].replaceAll("\"", "");
			values.add(null);
		}

		try {
			addDateTimeField(values, items, CDRFieldNames.answerTime);
			addField(values, items, CDRFieldNames.basicService);
			addNumberField(values, items, CDRFieldNames.callDuration);
			addField(values, items, CDRFieldNames.calledNumber);
			addField(values, items, CDRFieldNames.callingNumber);
			addField(values, items, CDRFieldNames.callReference);
			addField(values, items, CDRFieldNames.causeForTermination);
			addField(values, items, CDRFieldNames.cdrType);
			addField(values, items, CDRFieldNames.imei);
			addField(values, items, CDRFieldNames.imsi);
			addField(values, items, CDRFieldNames.location);
			addField(values, items, CDRFieldNames.mscAddress);
			addField(values, items, CDRFieldNames.recordingEntity);
			addDateTimeField(values, items, CDRFieldNames.releaseTime);

			addDateTimeFieldIgnoreParseError(values, items,
					CDRFieldNames.seizureTime);

			collector.emit(input, values);

		} catch (ParseException e) {
			LOGGER.warn("Ignoring Record due to exception : " + e.toString());
		} catch (NumberFormatException e) {
			LOGGER.warn("Ignoring Record due to exception : " + e.toString());
		}

		collector.ack(input);
	}

	void addField(Values values, String[] itemsToAdd, String fieldName) {

		values.set(CDRFieldNames.getIndexOfField(fieldName),
				itemsToAdd[CDRFieldNames.getIndexOfField(fieldName)]);
	}

	void addDateTimeField(Values values, String[] itemsToAdd, String fieldName)
			throws ParseException {

		values.set(CDRFieldNames.getIndexOfField(fieldName), dateFormatter
				.parse(itemsToAdd[CDRFieldNames.getIndexOfField(fieldName)]));

	}

	void addDateTimeFieldIgnoreParseError(Values values, String[] itemsToAdd,
			String fieldName) {

		try {
			values.set(CDRFieldNames.getIndexOfField(fieldName),
					dateFormatter.parse(itemsToAdd[CDRFieldNames
							.getIndexOfField(fieldName)]));
		} catch (ParseException e) {
			LOGGER.info("Ignoring Record due to exception : " + e.toString());

			values.set(CDRFieldNames.getIndexOfField(fieldName), null);
		}

	}

	void addNumberField(Values values, String[] itemsToAdd, String fieldName)
			throws ParseException {

		values.set(CDRFieldNames.getIndexOfField(fieldName),
				Float.parseFloat(itemsToAdd[CDRFieldNames
						.getIndexOfField(fieldName)]));

	}
}
