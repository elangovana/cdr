<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><decorator:title default="Call Detail Records Project" /></title>
<link href="<s:url value='/styles/main.css'/>" rel="stylesheet"
	type="text/css" media="all" />
<link href="<s:url value='/struts/niftycorners/niftyCorners.css'/>"
	rel="stylesheet" type="text/css" />
<link href="<s:url value='/struts/niftycorners/niftyPrint.css'/>"
	rel="stylesheet" type="text/css" media="print" />


<decorator:head />
</head>
<body id="page-home">
	<div id="page">
		<div id="header" class="clearfix">
			CDR Promo
			<hr />
		</div>

		<div id="content" class="clearfix">
			<div id="main">
				<h3><decorator:title /></h3>
				<decorator:body />
				<hr />
			</div>

			<div id="sub">
				<h3>Sub Content</h3>
			</div>


			<div id="local">
				<h3>
					<decorator:getProperty property="page.navTitle" />
				</h3>
				<decorator:getProperty property="page.localNavig"></decorator:getProperty>

			</div>


			<div id="nav">
				<div class="wrapper">

					<ul class="clearfix">
						<li><a href="index">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Home&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
						<li><a href="configure-rule-index">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rules&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
						<li><a href="reports-promo-calls">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reports&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
					
						<li class="last"><a href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Contact&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
					</ul>
				</div>
				<hr />
			</div>
		</div>

		<div id="footer" class="clearfix">Developed by AE</div>

	</div>

	<div id="extra1">&nbsp;</div>
	<div id="extra2">&nbsp;</div>
</body>
</html>
