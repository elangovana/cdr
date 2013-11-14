package com.ae.assignment.cdrproject.cdrstream.spout;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import com.ae.assignment.cdrproject.cdrstream.bolt.CDRRecordCreator;

public class FileNamesSpout extends BaseRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SpoutOutputCollector _collector;

	String[] _filesSearchPattern;
	String _baseSearchPath;
	String _workingDirectory;


	private final static Logger LOGGER = Logger
			.getLogger(CDRRecordCreator.class.getName());

	public FileNamesSpout(String baseSearchPath, String filesSearchPattern,
			String workingDirectory) {
		_filesSearchPattern = new String[] { filesSearchPattern };
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
		LOGGER.info(String.format("Search for files %s in %s",
				_filesSearchPattern[0], _baseSearchPath));
		while (it.hasNext()) {
			File currentFile = it.next().getAbsoluteFile();
			File movedFile = new File(_workingDirectory, currentFile.getName());
			try {
			
				
				FileUtils.moveFileToDirectory(currentFile, new File(
						_workingDirectory), true);
				_collector.emit(new Values(movedFile.getAbsolutePath()));

			} catch (IOException e) {
				LOGGER.error(String.format(
						"File %s could  not be moved from %s to %s. It could be because a file with the same name exists in the destination directory. ",
						currentFile.getAbsolutePath(), _baseSearchPath,
						_workingDirectory), e);

			}

		}
		Utils.sleep(1000 * 6);

	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_collector = collector;
		// move left over half processed files back to base directory
		moveFilesToDirectory(_workingDirectory, _filesSearchPattern,
				_baseSearchPath);
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Config conf = new Config();

		// run only a single instance of this bolt in the Storm topology
		conf.setMaxTaskParallelism(1);
		return conf;
	}

	static void moveFilesToDirectory(String baseSearchPath,
			String[] filesSearchPattern, String destinationDir) {
		Iterator<File> it = FileUtils.iterateFiles(new File(baseSearchPath),
				filesSearchPattern, false);
		LOGGER.info(String.format("Search for  files %s in %s",
				filesSearchPattern[0], baseSearchPath));
		while (it.hasNext()) {
			File currentFile = it.next().getAbsoluteFile();
			File movedFile = new File(destinationDir, currentFile.getName());
			try {

				FileUtils.moveFileToDirectory(currentFile, new File(
						destinationDir), true);

			} catch (IOException e) {
				LOGGER.error(String.format(
						"File %s could  not be moved from %s to %s ",
						currentFile.getAbsolutePath(), baseSearchPath,
						destinationDir), e);
			}
		}
	}
}
