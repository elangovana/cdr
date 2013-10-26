<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Configure Top N Promo Rule</title>
<s:head />
</head>
<body>



	<div id="content-body-wrapper">

		<div id="content-body">
			<div id="primary-nav">Rule Description</div>
			<div id="secondary-nav">
				<s:property value="ruleConfigTopNCallers.ruleDescription" />
			</div>
		</div>

		<div id="content-body">
			<div id="primary-nav">Promo Text</div>
			<div id="secondary-nav">
				<s:property value="ruleConfigTopNCallers.promo" />
			</div>
		</div>

		<div id="content-body">
			<div id="primary-nav">Top N % </div>
			<div id="secondary-nav">
				<s:property value="ruleConfigTopNCallers.topCallersPercentage" />
			</div>
		</div>

		<div id="content-body">
			<div id="primary-nav">Total Period in Days</div>
			<div id="secondary-nav">
				<s:property value="ruleConfigTopNCallers.calculationPeriodInDays" />
			</div>
		</div>
		<div id="content-body">
			<div id="primary-nav">Is Active</div>
			<div id="secondary-nav">
				<s:property value="ruleConfigTopNCallers.isActive" />
			</div>
		</div>
		<div id="content-body">
			<div id="primary-nav"></div>
			<div id="secondary-nav">
				<a href="<s:url action="configure-rule-topn-callers-promo-edit"/>">
					Edit</a>

			</div>
		</div>
	</div>






	<%@ include file="menu/configure-rule-menu.htm"%>


</body>
</html>
