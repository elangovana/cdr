package com.ae.assignment.cdrproject.cdrstream.algorithm;

import java.io.Serializable;

public interface IDeduplicate<T>  extends Serializable{

	boolean Exists(T item);
	
	void Add(T item);
}
