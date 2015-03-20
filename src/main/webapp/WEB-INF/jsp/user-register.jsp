<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../layout/taglib.jsp"%>


<!-- This form needs to bind to an object. Hence in the Controller, create a ModelAttribute -->
<!-- We will perform post request to the same URL. Verify from the source of the page, action="/register".
So add method to user controller class which will do registration.-->
<form:form commandName="user" cssClass="form-horizontal">
	
	<c:if test="${param.success eq true }">
		<div class="alert alert-success"> Registration Successful!</div>
	</c:if>
	
	<div class="form-group">
		<label for="name" class="col-sm-2 control-label">Name</label>
		<div class="col-sm-3">
			<form:input path="name" cssClass="form-control" />
			<!-- errors added for step 42, server side validation -->
			<form:errors path="name"/>
		</div>
	</div>

	<div class="form-group">
		<label for="email" class="col-sm-2 control-label">Email</label>
		<div class="col-sm-3">
			<form:input path="email" cssClass="form-control" />
			<!-- errors added for step 42, server side validation -->
			<form:errors path="email"/>
		</div>
	</div>

	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password:
		</label>
		<div class="col-sm-3">
			<form:password path="password" cssClass="form-control" />
			<!-- errors added for step 42, server side validation -->
			<form:errors path="password"/>
		</div>
	</div>

	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
		<input type="submit" value="Save" class="btn btn-lg btn-primary"/>
		</div>
	</div>

</form:form>

