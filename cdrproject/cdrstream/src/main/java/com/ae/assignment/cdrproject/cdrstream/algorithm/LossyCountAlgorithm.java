package com.ae.assignment.cdrproject.cdrstream.algorithm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author AE http://www.vldb.org/conf/2002/S10P03.pdf
 * 
 * @param <TItem>
 *            Item to count
 * 
 * 
 */
public class LossyCountAlgorithm<TItem> implements IFrequencyCalculator<TItem>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896974003749699689L;

	// private
	HashMap<TItem, FrequencyBucket> _buckets;
	long _counter;
	int _delta;
	float _error, supportThreshold;

	public LossyCountAlgorithm() {
		this(.0001f);
	}

	/**
	 * 
	 * @param supportThreshold
	 *            ; support threshold must be between ( 0 1); For instance, to
	 *            select only items whose frequency is at least .1% of the
	 *            stream seen so far, then s=.1%.
	 * 
	 */
	public LossyCountAlgorithm(float supportThreshold) {
		this(supportThreshold, supportThreshold / 10);
	}

	/**
	 * 
	 * @param supportThreshold
	 *            ; support threshold must be between ( 0 1); For instance, to
	 *            select only items whose frequency is at least .1% of the
	 *            stream seen so far, then s=.1%.
	 * 
	 * @param errorMargin
	 *            : Error margin must be 0 << 1; it must also be much lesser
	 *            than support threshold. The rule of thumb is it must be set to
	 *            one tenth or one twentieth of the support threshold.
	 * 
	 */
	public LossyCountAlgorithm(float supportThreshold, float errorMargin)
			throws IllegalArgumentException {

		validateThreshold(supportThreshold, errorMargin);

		_buckets = new HashMap<TItem, LossyCountAlgorithm<TItem>.FrequencyBucket>();
		_error = errorMargin;
		this.supportThreshold = supportThreshold;
	}

	private void validateThreshold(float supportThreshold, float errorMargin) {
		if (supportThreshold <= 0 || supportThreshold >= 1) {
			throw new IllegalArgumentException(
					String.format(
							"Invalid Parameter supportThreshold, Value %1$ must be between 0 and 1.",
							supportThreshold));
		}

		if (errorMargin <= 0 || errorMargin >= 1
				|| errorMargin >= supportThreshold) {
			throw new IllegalArgumentException(
					String.format(
							"Invalid Parameter errorMargin, Value %1$ must be between 0 and 1 and less than the support threshold %2$.",
							errorMargin, supportThreshold));
		}
	}

	@Override
	public void add(TItem item) {
		_counter++;

		FrequencyBucket frequencyBucket = new FrequencyBucket();

		if (_buckets.containsKey(item)) {
			// Increment Existing Item
			frequencyBucket.frequency = _buckets.get(item).frequency + 1;
			_buckets.put(item, frequencyBucket);
		} else {
			// Add New Item
			frequencyBucket.frequency = 1 + _delta;
			frequencyBucket.bucket--;
			_buckets.put(item, frequencyBucket);
		}

		removeLowMarginItems();

	}

	private void removeLowMarginItems() {

		if (Math.floor(_counter * (double) _error) == _delta)
			return;
		// Remove all low margin items;

		_delta = (int) Math.floor(_counter * (double) _error);

		for (Iterator<Map.Entry<TItem, FrequencyBucket>> i = _buckets
				.entrySet().iterator(); i.hasNext();) {

			Map.Entry<TItem, FrequencyBucket> entry = i.next();

			if (entry.getValue().frequency < _delta) {
				i.remove();
			}
		}

	}

	@Override
	public HashMap<TItem, Integer> getCurrentItemsFrequencies() {
		HashMap<TItem, Integer> ret = new HashMap<TItem, Integer>();
		for (TItem itemKey : _buckets.keySet()) {
			ret.put(itemKey, _buckets.get(itemKey).frequency);
		}

		return ret;
	}

	@Override
	public void reset() {
		_buckets.clear();
		_counter = 0;
		_delta = 0;

	}

	class FrequencyBucket {
		public int frequency;
		public int bucket;
	}

	@Override
	public void setSupportThreshold(float supportThreshold) {
		validateThreshold(supportThreshold, this._error);
		this.supportThreshold = supportThreshold;
		this._error = supportThreshold/10;
	}
}
