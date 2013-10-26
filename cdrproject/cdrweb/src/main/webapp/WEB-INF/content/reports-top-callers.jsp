<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>


<title>Promos</title>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"
	type="text/javascript">
	
</script>
<link href="<s:url value='/styles/main.css'/>" rel="stylesheet"
	type="text/css" media="all" />

<script src="<s:url value='/js/highcharts.js' />"></script>
<script src="<s:url value='/js/modules/exporting.js' />"></script>
<script src="<s:url value='/js/cdr/cdrcharts.js' />"></script>
<script >

function refreshChart() {
	createBarChart("#frmStats", "#recentChart", "Top Callers For Today", "Top Callers", "Calls made" );
	$("#refreshedDate").html(new Date().toString());
	   setTimeout(refreshChart,30000);

	}

$(document).ready(function (){
	refreshChart();
	});

</script>

</head>

<body>

	<div id="frmStats" hidden="true">
		<s:url action="reports-top-callers-retrieveReportData" /></div>

	
	<div id="recentChart"></div>

	<div id="recentPromosChartLog"></div>
	
	<div id="refeshedON">
	Last Updated On:
	<span id="refreshedDate"></span>
	</div>

	<%@ include file="menu/reports-menu.htm"%>
</body>
</html>