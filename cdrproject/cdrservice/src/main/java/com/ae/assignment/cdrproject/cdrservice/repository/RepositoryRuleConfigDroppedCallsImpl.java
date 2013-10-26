package com.ae.assignment.cdrproject.cdrservice.repository;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;

@Repository
public class RepositoryRuleConfigDroppedCallsImpl extends
		RepositoryRuleConfigBaseImpl<RuleConfigDroppedCalls> implements
		RepositoryRuleConfigDroppedCalls, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1618387957062544955L;

	

}
