package com.ae.assignment.cdrproject.cdrstream.bolt;

import backtype.storm.Constants;
import backtype.storm.tuple.Tuple;

public class TupleHelper {

	 public static boolean isTickTuple(Tuple tuple) {
	        return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
	            && tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID);
	    }
 
}
