package com.ae.assignment.cdrproject.cdrservice;

import java.io.Serializable;
import java.util.Date;

public interface ServiceRuleConfig<T> extends Serializable  {


	String getRuleName();
	
	String getRuleTemplateName();
	
	void Save(T rule);
	
	T Load();	
	
	T GetRuleLastUpdatedSince(Date lastUpdatedOn);
	
}
