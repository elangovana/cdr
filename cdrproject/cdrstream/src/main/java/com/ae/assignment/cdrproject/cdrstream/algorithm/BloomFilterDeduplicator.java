package com.ae.assignment.cdrproject.cdrstream.algorithm;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.apache.hadoop.util.bloom.BloomFilter;
import org.apache.hadoop.util.bloom.Key;
import org.apache.hadoop.util.hash.MurmurHash;

/**
 * 
 * @author streamsadmin
 * 
 * @param <T>
 */
public class BloomFilterDeduplicator<T> implements IDeduplicate<T> , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1960014940481216368L;
	
	transient BloomFilter filter;

	private BloomFilter getFilter() {
		if (filter == null)
		{
			createBloomFilter(approximateInsertSize, falsePositiveRate);
		}
		return filter;
	}

	private void setFilter(BloomFilter filter) {
		this.filter = filter;
	}

	private int approximateInsertSize;

	private float falsePositiveRate;



	public BloomFilterDeduplicator(int approximateInsertSize,
			float falsePositiveRate) {
		
		this.approximateInsertSize = approximateInsertSize;
		this.falsePositiveRate = falsePositiveRate;
	}

	private void createBloomFilter(int approximateInsertSize,
			float falsePositiveRate) {
		int vectorSize = caculateBitLength(approximateInsertSize,
				falsePositiveRate);
		int hashLength = 4;
		filter = new BloomFilter(vectorSize, hashLength,
				MurmurHash.MURMUR_HASH);
	}

	@Override
	public void Add(T item) {
		getFilter().add(getKey(item));

	}

	private int caculateBitLength(int approximateInsertSize,
			float falsePositiveRate) {
		return (int) ((falsePositiveRate / 0.6185) * (approximateInsertSize));

	}


	@Override
	public boolean Exists(T item) {
		return getFilter().membershipTest(getKey(item));
	}

	Key getKey(T item) {
		return new Key(ByteBuffer.allocate(5).putInt(item.hashCode()).array());
	}

}
