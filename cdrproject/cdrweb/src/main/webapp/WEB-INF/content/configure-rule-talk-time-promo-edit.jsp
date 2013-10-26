<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Configure Talk Time Promo Rule</title>
<s:head />
</head>
<body>

	<s:form action="configure-rule-talk-time-promo-submit">
		<s:hidden value="ruleConfigTalkTime.ruleName" />


		<s:textarea label="Rule Description"
			name="ruleConfigTalkTime.ruleDescription" />

		<s:textarea label="Promo Text" name="ruleConfigTalkTime.promo" />

		<s:textfield label="Call Duration in mins"
			name="ruleConfigTalkTime.callDurationInMins" />

		<s:textfield label="Total Period in Days"
			name="ruleConfigTalkTime.calculationPeriodInDays" />

		<s:checkbox label="Is Active" name="ruleConfigTalkTime.isActive" />



		<s:submit />
	</s:form>



	<%@ include file="menu/configure-rule-menu.htm"%>



</body>
</html>
