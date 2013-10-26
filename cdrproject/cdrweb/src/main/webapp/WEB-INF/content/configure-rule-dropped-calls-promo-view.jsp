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

	<div id="content-body-wrapper">

		<div id="content-body">
			<div id="primary-nav">Rule Description</div>
			<div id="secondary-nav">
				<s:property value="droppedCallsPromoRuleConfig.ruleDescription" />
			</div>
		</div>


		<div id="content-body">
			<div id="primary-nav">Promo</div>
			<div id="secondary-nav">
				<s:property value="droppedCallsPromoRuleConfig.promo" />
			</div>
		</div>

		<div id="content-body">
			<div id="primary-nav">Number of Calls Dropped</div>
			<div id="secondary-nav">
				<s:property value="droppedCallsPromoRuleConfig.nbCallsDropped" />
			</div>
		</div>



		<div id="content-body">
			<div id="primary-nav">Calculation Period In days</div>
			<div id="secondary-nav">
				<s:property
					value="droppedCallsPromoRuleConfig.nbCallsDroppedDurationInDays" />
			</div>
		</div>

		<div id="content-body">
			<div id="primary-nav">Is Active</div>
			<div id="secondary-nav">
				<s:property value="droppedCallsPromoRuleConfig.isActive" />
			</div>
		</div>


		<div id="content-body">
			<div id="primary-nav"></div>
			<div id="secondary-nav">
				<a href="<s:url action="configure-rule-dropped-calls-promo-edit"/>">
					Edit</a>

			</div>
		</div>



	</div>



	<%@ include file="menu/configure-rule-menu.htm"%>

</body>
</html>
