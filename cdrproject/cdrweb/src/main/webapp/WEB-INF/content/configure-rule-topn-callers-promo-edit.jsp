<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Configure Top N Callers Promo Rule</title>
<s:head />
</head>
<body>

	<s:form action="configure-rule-topn-callers-promo-submit">
		<s:hidden value="ruleConfigTopNCallers.ruleName" />


		<s:textarea label="Rule Description"
			name="ruleConfigTopNCallers.ruleDescription" />

		<s:textarea label="Promo Text" name="ruleConfigTopNCallers.promo" />

		<s:textfield label="Top N % Callers"
			name="ruleConfigTopNCallers.topCallersPercentage" />

		<s:textfield label="Total Period in Days"
			name="ruleConfigTopNCallers.calculationPeriodInDays" />

		<s:checkbox label="Is Active" name="ruleConfigTopNCallers.isActive" />



		<s:submit />
	</s:form>



	<%@ include file="menu/configure-rule-menu.htm"%>



</body>
</html>
