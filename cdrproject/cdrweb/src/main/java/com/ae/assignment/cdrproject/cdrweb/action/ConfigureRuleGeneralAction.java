package com.ae.assignment.cdrproject.cdrweb.action;

import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionSupport;

public class ConfigureRuleGeneralAction extends ActionSupport {
    
  
	@Action("configure-rule-index")
    public String execute() throws Exception {
      
        return SUCCESS;
    }
    
    @Action("configure-rule-update-complete")
    public String rulesUpdated() throws Exception {
	      
        return SUCCESS;
    }

}
