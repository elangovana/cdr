package com.ae.assignment.cdrproject.cdrstream.bolt;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import clojure.string__init;
import backtype.storm.tuple.Fields;

public class CDRFieldNames {

	public static HashMap<String, Integer> getAllfieldsmap() {
		return AllFieldsMap;
	}

	
	
	static final HashMap<String, Integer> AllFieldsMap = loadFields();
		
	
	
	public static final String DroppedCallCode = "04";

	public static final String cdrRawLine = "cdrRaw";

	public static final String fileLine = "line";

	public static final String cdr = "cdr";

	public static final String cdrType = "cdrType";

	public static final String callingNumber = "CallingNumber";

	public static final String calledNumber = "CalledNumber";

	public static final String answerTime = "answerTime";

	public static final String causeForTermination = "causeForTermination";

	public static final String seizureTime = "seizureTime";

	public static final String DateTimeFormat = "yyyy-MM-dd HH:mm:ss";

	public static final String imsi = "imsi";

	public static final String imei = "imei";

	public static final String recordingEntity = "recordingEntity";

	public static final String location = "location";
	public static final String callReference = "callReference";
	public static final String callDuration = "callDuration";

	public static final String releaseTime = "releaseTime";

	public static final String basicService = "basicService";
	public static final String mscAddress = "mscAddress";

	public static Fields getFieldsCDRRecord() {
		return new Fields(cdrType,  imsi, imei, callingNumber,
				calledNumber, recordingEntity, location, callReference,
				callDuration, answerTime, seizureTime, releaseTime,
				causeForTermination, basicService, mscAddress);
	}

	public static Fields getFieldsFileLine() {
		return new Fields(fileLine);
	}

	public static HashMap<String, Integer> loadFields() {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		result.put(cdrType, 0);
		result.put(imsi, 1);
		result.put(imei, 2);
		result.put(callingNumber, 3);
		result.put(calledNumber, 4);
		result.put(recordingEntity, 5);
		result.put(callReference, 6);
		result.put(location, 7);
		result.put(callDuration, 8);
		result.put(answerTime, 9);
		result.put(seizureTime, 10);
		result.put(releaseTime, 11);
		result.put(causeForTermination, 12);
		result.put(basicService, 13);
		result.put(mscAddress, 14);

		return result;
	}

	public static int getIndexOfField(String fieldName) {

		if (getAllfieldsmap().containsKey(fieldName))
			return getAllfieldsmap().get(fieldName);

		throw new RuntimeException(new IllegalArgumentException(String.format(
				"The fieldname %s is not valid for a cdr record", fieldName)));
	}

}
