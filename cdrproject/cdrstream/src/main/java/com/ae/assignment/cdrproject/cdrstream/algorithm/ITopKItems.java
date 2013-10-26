package com.ae.assignment.cdrproject.cdrstream.algorithm;

/** 
 * 
 * @author AE
 *
 * @param <TItem> The type of item that needs to be tracked.
 */
import java.util.*;

public interface ITopKItems<TItem>{

	void add(TItem item);
	
	HashMap<TItem , Integer> getCurrentTopItems();
	
	void reset();
	
	 void setTopNCount(int topNCount) ;
	
}
