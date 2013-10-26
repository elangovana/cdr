package com.ae.assignment.cdrproject.cdrstream.spout;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.ae.assignment.cdrproject.cdrstream.bolt.CDRRecordCreator;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class FileNamesSpout extends BaseRichSpout {
	SpoutOutputCollector _collector;

	String[] _filesSearchPattern;
	String _baseSearchPath;
	String _workingDirectory;

	private final static Logger LOGGER = Logger
			.getLogger(CDRRecordCreator.class.getName());

	public FileNamesSpout(String baseSearchPath, String filesSearchPattern,
			String workingDirectory) {
		_filesSearchPattern = new String[] {  filesSearchPattern};
		_baseSearchPath = baseSearchPath;
		_workingDirectory = workingDirectory;
	}

	@Override
	public void ack(Object id) {
	}

	@Override
	public void close() {

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("fileName"));
	}

	@Override
	public void fail(Object id) {
	}

	@Override
	public void nextTuple() {

		Iterator<File> it = FileUtils.iterateFiles(new File(_baseSearchPath),
				_filesSearchPattern, false);
		LOGGER.info(String.format("Search for files %s in %s",_filesSearchPattern[0], _baseSearchPath));
		while (it.hasNext()) {
			File currentFile = it.next().getAbsoluteFile();
			File movedFile = new File(_workingDirectory, currentFile.getName());
			try {

				FileUtils.moveFileToDirectory(currentFile, new File(
						_workingDirectory), true);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			_collector.emit(new Values(movedFile.getAbsolutePath()));
		}
		Utils.sleep(1000 * 6);

	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_collector = collector;

	}
	@Override
	public Map<String, Object> getComponentConfiguration() {
		 Config conf = new Config();   
	  
	    // run only a single instance of this bolt in the Storm topology
	    conf.setMaxTaskParallelism(1);
	    return conf;
	}
}
