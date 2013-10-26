package com.ae.assignment.cdrproject.cdrstream.bolt;

public class TopologyConstants {
	
	public final static String StreamIDRules ="Rules";
	public final static String StreamIDCDRRecord ="CDRRecord";
	public final static String StreamIDRuleTopNCallers ="RuleTopNCallersBolt";
	public static final String DROPPED_CALLS_FILTER = "DroppedCallsFilter";
	public  static final String DROPPED_CALLS_RULE_EVALUATOR_BOLT = "DroppedCallsRuleEvaluatorBolt";
	public static final String TALK_TIME_RULE_EVALUATOR_BOLT = "TalkTimeRuleEvaluatorBolt";
	public static final String DUPLICATE_CALL_FILTER = "DuplicateCallFilter";
	public static final String CDR_FILE_LINE_SPLITTER = "CDRFileLineSplitter";
	public static final String CDR_WORKLOAD_GEN = "CDRWorkloadGen";
	public static final String FILES_NAMES_SPOUT = "FilesNamesSpout";

}
