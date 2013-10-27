package com.ae.assignment.cdrproject.cdrstream;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.ae.assignment.cdrproject.cdrstream.bolt.CDRFieldNames;
import com.ae.assignment.cdrproject.cdrstream.bolt.CDRRecordCreator;
import com.ae.assignment.cdrproject.cdrstream.bolt.CDRWorkloadGen;
import com.ae.assignment.cdrproject.cdrstream.bolt.DroppedCallsFilterBolt;
import com.ae.assignment.cdrproject.cdrstream.bolt.DroppedCallsRuleEvaluatorBolt;
import com.ae.assignment.cdrproject.cdrstream.bolt.DuplicateRecordFilterBolt;
import com.ae.assignment.cdrproject.cdrstream.bolt.TalkTimeRuleEvaluatorBolt;
import com.ae.assignment.cdrproject.cdrstream.bolt.TopNCallersRuleEvaluatorBolt;
import com.ae.assignment.cdrproject.cdrstream.bolt.TopologyConstants;
import com.ae.assignment.cdrproject.cdrstream.spout.FileNamesSpout;
import com.ae.assignment.cdrproject.cdrstream.spout.RulesSpout;

public class CDRTopology {

	static boolean _debug;
	static int _runforNsec;
	static int emitSleepTimeInMillSec;
	static int approximateInsertSize;
	static float falsePostiveRate;
	static int nbInstancesCDRWokload;
	static int nbInstancesDeDuplicateFilter;
	static int nbInstancesDropCallFilter;
	static int nbInstancesFileLineSplitter;
	static int nbInstancesTalkTimeRuleEvaluator;
	static int nbInstancesTopCallersEvaluator;
	static int nbInstancesDropedCallersEvaluator;

	public static void main(String[] args) throws Exception {

		if (args != null && args.length > 1) {
			printUsage();
			throw new IllegalArgumentException("Invalid Arguments");
		}
		LoadProperties();

		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		TopologyBuilder builder = buildTopology(args, appContext);

		Config conf = new Config();
		conf.setDebug(_debug);

		if (args.length == 1) {
			conf.setNumWorkers(3);

			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
		} else {
			conf.setMaxTaskParallelism(3);

			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("CDRTopology", conf,
					builder.createTopology());

			Thread.sleep(_runforNsec * 1000);

			cluster.shutdown();
		}

	}

	private static TopologyBuilder buildTopology(String[] args,
			ApplicationContext appContext) {

		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout(TopologyConstants.FILES_NAMES_SPOUT,
				appContext.getBean(FileNamesSpout.class), 1);

		builder.setSpout(TopologyConstants.StreamIDRules,
				appContext.getBean(RulesSpout.class), 1);

		builder.setBolt(TopologyConstants.CDR_WORKLOAD_GEN,
				appContext.getBean(CDRWorkloadGen.class), 8).shuffleGrouping(
				TopologyConstants.FILES_NAMES_SPOUT);

		builder.setBolt(TopologyConstants.CDR_FILE_LINE_SPLITTER,
				new CDRRecordCreator(), nbInstancesFileLineSplitter).shuffleGrouping(
				TopologyConstants.CDR_WORKLOAD_GEN);

		builder.setBolt(
				TopologyConstants.DUPLICATE_CALL_FILTER,
				new DuplicateRecordFilterBolt(approximateInsertSize,
						falsePostiveRate), nbInstancesDeDuplicateFilter).fieldsGrouping(
				TopologyConstants.CDR_FILE_LINE_SPLITTER,
				new Fields(CDRFieldNames.callingNumber,
						CDRFieldNames.calledNumber));

		builder.setBolt(TopologyConstants.DROPPED_CALLS_FILTER,
				new DroppedCallsFilterBolt(), nbInstancesDropCallFilter).shuffleGrouping(
				TopologyConstants.DUPLICATE_CALL_FILTER);

		builder.setBolt(TopologyConstants.TALK_TIME_RULE_EVALUATOR_BOLT,
				appContext.getBean(TalkTimeRuleEvaluatorBolt.class), nbInstancesTalkTimeRuleEvaluator)
				.fieldsGrouping(TopologyConstants.DUPLICATE_CALL_FILTER,
						new Fields(CDRFieldNames.callingNumber))
				.allGrouping(TopologyConstants.StreamIDRules);

		builder.setBolt(TopologyConstants.DROPPED_CALLS_RULE_EVALUATOR_BOLT,
				appContext.getBean(DroppedCallsRuleEvaluatorBolt.class), nbInstancesDropedCallersEvaluator)
				.fieldsGrouping(TopologyConstants.DROPPED_CALLS_FILTER,
						new Fields(CDRFieldNames.callingNumber))
				.allGrouping(TopologyConstants.StreamIDRules);

		builder.setBolt(TopologyConstants.StreamIDRuleTopNCallers,
				appContext.getBean(TopNCallersRuleEvaluatorBolt.class), nbInstancesTopCallersEvaluator)
				.fieldsGrouping(TopologyConstants.DUPLICATE_CALL_FILTER,
						new Fields(CDRFieldNames.callingNumber))
				.allGrouping(TopologyConstants.StreamIDRules);

		return builder;
	}

	static void printUsage() {
		System.out.println("[topologyName]");
	}

	static void LoadProperties() throws Exception {
		Properties prop = new Properties();
		prop.load(CDRTopology.class.getClassLoader().getResourceAsStream(
				"config.properties"));

		_runforNsec = Integer.parseInt(prop
				.getProperty("LocalClusterRunForSeconds"));

		_debug = Boolean.parseBoolean(prop.getProperty("Debug"));
		emitSleepTimeInMillSec = Integer.parseInt(prop
				.getProperty("CDRWorkLoadGen.EmitSleepTimeInMillSec"));

		approximateInsertSize = Integer
				.parseInt(prop
						.getProperty("DuplicateCheck.ApproximateTotalRecordsInsertSize"));

		falsePostiveRate = Float.parseFloat(prop
				.getProperty("DuplicateCheck.FalsePostiveRate"));
		
		nbInstancesCDRWokload = Integer
				.parseInt(prop
						.getProperty("Topology.InstancesCDRWWorkLoadGen"));
		
		nbInstancesDeDuplicateFilter = Integer
				.parseInt(prop
						.getProperty("Topology.InstancesDeDuplicateFilter"));
		
		nbInstancesDropCallFilter = Integer
				.parseInt(prop
						.getProperty("Topology.InstancesDropCallFilter"));
		
		nbInstancesDropedCallersEvaluator = Integer
				.parseInt(prop
						.getProperty("Topology.InstancesDropedCallersEvaluator"));
		
		nbInstancesFileLineSplitter = Integer
				.parseInt(prop
						.getProperty("Topology.InstancesFileLineSplitter"));
		
		nbInstancesTalkTimeRuleEvaluator = Integer
				.parseInt(prop
						.getProperty("Topology.InstancesTalkTimeRuleEvaluator"));
		
		nbInstancesTopCallersEvaluator = Integer
				.parseInt(prop
						.getProperty("Topology.InstancesTopCallersEvaluator"));
		
		
		


	}

}
