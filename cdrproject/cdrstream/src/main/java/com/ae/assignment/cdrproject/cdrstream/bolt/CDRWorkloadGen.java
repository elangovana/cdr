package com.ae.assignment.cdrproject.cdrstream.bolt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import com.ae.assignment.cdrproject.cdrservice.ServiceLogImpl;
import com.ae.assignment.cdrproject.cdrservice.ServiceLog;

public class CDRWorkloadGen extends BaseRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2703951273801135745L;
	OutputCollector _collector;
	BufferedReader _buffer;
	ServiceLog logService;

	public void setLogService(ServiceLog logService) {
		this.logService = logService;
	}

	int linesRead;
	private int sleepTimeInMillSec;

	public CDRWorkloadGen(int emitSleepTimeInMillSec) {
		this.sleepTimeInMillSec = emitSleepTimeInMillSec;
		logService = new ServiceLogImpl();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(CDRFieldNames.getFieldsFileLine());
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		_collector = collector;

	}

	private void openFile(String fileName) {
		try {
			_buffer = new BufferedReader(new FileReader(fileName));
			linesRead = logService.retrieveLinesRead(fileName);
			int i = 0;
			while (_buffer.readLine() != null && i < linesRead) {
				i++;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void execute(Tuple input) {
		String sentence;
		try {
			String fileName = input.getString(0);
			openFile(fileName);
			while ((sentence = _buffer.readLine()) != null) {	
				//TODO check if this needs to be anchored to the fileName tuple
				_collector.emit(new Values(sentence));
				linesRead++;
				logService.putLinesRead(fileName, linesRead);				
				Utils.sleep(sleepTimeInMillSec);
			}

			_collector.ack(input);
			_buffer.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
