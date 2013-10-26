package com.ae.assignment.cdrproject.cdrstream.algorithm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class TopKFrequentAlgorithm<TItem> implements ITopKItems<TItem>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 334146078553968907L;
	
	HashMap<TItem, Integer> _topList;
	int topNCount;




	public int getTopNCount() {
		return topNCount;
	}

	public void setTopNCount(int topNCount) {
		this.topNCount = topNCount;
	}

	public TopKFrequentAlgorithm(int topNCount) {
		this.topNCount = topNCount;
		_topList = new HashMap<TItem, Integer>(topNCount);

	}
	
	public TopKFrequentAlgorithm() {
		this(20);

	}

	@Override
	public void add(TItem item) {
		Integer count = _topList.get(item);
		
		if (count != null) {
			count++;
			_topList.put(item, count);

		} else if (_topList.size() < topNCount) {
			_topList.put(item, 1);

		} else {
			for (Iterator<Map.Entry<TItem, Integer>> i = _topList.entrySet()
					.iterator(); i.hasNext();) {
				
				Map.Entry<TItem, Integer> itemEntry = i.next();
				TItem itemKey = itemEntry.getKey();
				
				count = itemEntry.getValue();
				count--;				
				_topList.put(itemKey, count);
				
				if (count == 0)
					i.remove();
			}
		}

	}

	@Override
	public HashMap<TItem, Integer> getCurrentTopItems() {
		return _topList;
	}

	@Override
	public void reset() {
		_topList.clear();
		
	}

}
