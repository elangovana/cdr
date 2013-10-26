package com.ae.assignment.cdrproject.cdrservice.repository;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigBase;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTalkTime;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;

@Repository
public class RepositoryRuleConfigTalkTimeImpl extends RepositoryRuleConfigBaseImpl<RuleConfigTalkTime> implements RepositoryRuleConfigTalkTime, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8585425181247093299L;

	
}
