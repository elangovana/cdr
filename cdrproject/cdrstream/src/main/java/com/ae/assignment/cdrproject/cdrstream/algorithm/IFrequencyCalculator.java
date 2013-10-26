package com.ae.assignment.cdrproject.cdrstream.algorithm;

import java.util.HashMap;

public interface IFrequencyCalculator<TItem> {

	void add(TItem item);

	HashMap<TItem, Integer> getCurrentItemsFrequencies();
	
	void setSupportThreshold(float supportThreshold);
	
	/**
	 * flushes all data and calculations and starts counting frequencies for the new set of incoming items
	 */
	void reset();
}
