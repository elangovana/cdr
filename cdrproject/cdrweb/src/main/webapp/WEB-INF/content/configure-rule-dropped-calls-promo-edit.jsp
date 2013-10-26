<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Dropped calls Rule Configuration</title>
<s:head />
</head>
<body>

	<s:form action="configure-rule-dropped-calls-promo-submit">
		<s:hidden value="droppedCallsPromoRuleConfig.ruleName" />


		<s:textarea label="Rule Description"
			name="droppedCallsPromoRuleConfig.ruleDescription" />

		<s:textarea label="Promo Text"
			name="droppedCallsPromoRuleConfig.promo" />

		<s:textfield label="Calls Dropped"
			name="droppedCallsPromoRuleConfig.nbCallsDropped" />

		<s:textfield label="Drop Duration in Days"
			name="droppedCallsPromoRuleConfig.nbCallsDroppedDurationInDays" />

		<s:checkbox label="Is Active"
			name="droppedCallsPromoRuleConfig.isActive" />

		<s:submit />
	</s:form>



	<%@ include file="menu/configure-rule-menu.htm"%>

</body>
</html>
