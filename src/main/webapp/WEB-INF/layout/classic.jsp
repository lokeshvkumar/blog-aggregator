<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tile"%>

<!-- this taglib was added as part of step 32, in order to authorize tabs to users -->
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>


<!-- use short cut - type 'jsp t' ctrl+space and taglib directive will pop up, select spring framework tags uri-->
<!-- refer tags with namespace-->
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- Apache tiles provide taglibs to look up for attributes defined for templates in general.xml. -->
<!-- tiles-extras library is not required in pom, can be removed. -->
<tile:useAttribute name="current" />

<!DOCTYPE html>
<!-- This mean we use HTML 5 -->
<html>
<head>
<!-- Copy the below three lines to point to bootstrap cdn -->
<!-- below code works for modal -->

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- step 43, jquery validation. add the jquery validation plugin reference to script section. -->
<script type="text/javascript"  src=""/>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tile:getAsString name="title" /></title>
</head>
<body>
	<!-- Static navbar -->
	<nav class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<spring:url value="/"/>">Blog
					Aggregator</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<!-- Choosing which page needs to be active depends on the selected tab item
          define an attribute for each template definition in general.xml and look up value based on the 
          current value and accordingly activate the selected page tab.-->
					<li class="${current == 'index' ? 'active': ''}"><a
						href="<spring:url value="/"/>">Home</a></li>
					<!-- added as part of step 32, user can see users tab only if the user has ROLE_ADMIN role. -->

					<security:authorize access="hasRole('ROLE_ADMIN')">
						<li class="${current == 'users' ? 'active': ''}"><a
							href="<spring:url value="/users"/>">Users</a></li>
					</security:authorize>
					<!-- step 26, added another tab to show user register form. -->
					<li class="${current == 'register' ? 'active': ''}"><a
						href="<spring:url value="/register"/>">Register</a></li>
					<!-- step 32, added taglib along with section below to use functions
            to verify authorization. Refer documenation under 
            http://docs.spring.io/spring-security/site/docs/3.2.6.RELEASE/reference/htmlsingle/#ns-config.
            So not authenticated, we will show login page -->
					<security:authorize access="!isAuthenticated()">
						<!-- step 29, added another tab added for login form. -->
						<li class="${current == 'login' ? 'active': ''}"><a
							href="<spring:url value="/login"/>">Login</a></li>
					</security:authorize>
					<!-- If authenticated then show logout.   -->
					<security:authorize access="isAuthenticated()">
						<!-- The my account section was added as part of step 37. -->
						<!-- as part of step 43, current was changed to account based on new definition from
						general.xml. 2ndly, in usercontroller, when user clicks myaccount, user-account.jsp will be returned. -->
						<li class="${current == 'account' ? 'active': ''}"><a
							href="<spring:url value="/account"/>">My Account</a></li>
						<li><a href="<spring:url value="/logout"/>">Logout</a></li>
					</security:authorize>

				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

	<!--/.nav-collapse -->

	<tile:insertAttribute name="body" />

	<br>
	<br>
	<center>
		<tile:insertAttribute name="footer" />
		<center>
			</div>
</body>
</html>