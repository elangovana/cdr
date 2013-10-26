package com.ae.assignment.cdrproject.cdrservice.repository;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTalkTime;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigDroppedCalls;
import com.ae.assignment.cdrproject.cdrservice.model.RuleConfigTopNCallers;

@Repository
public class RepositoryRuleConfigTopNCallersImpl extends RepositoryRuleConfigBaseImpl<RuleConfigTopNCallers> implements RepositoryRuleConfigTopNCallers, Serializable {

	/**
	 * 
	 */
	

}
