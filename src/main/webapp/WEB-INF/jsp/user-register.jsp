<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../layout/taglib.jsp"%>


<!-- This form needs to bind to an object. Hence in the Controller, create a ModelAttribute -->
<!-- We will perform post request to the same URL. Verify from the source of the page, action="/register".
So add method to user controller class which will do registration.-->
<form:form commandName="user"
	cssClass="form-horizontal registrationForm">
	<!-- step 48, instead of using param.success, just user success eq true. This is because redirect attributes is being used. 
	On register page, submission works the same, url does not show the parameters. Also on refresh it will go back to register form. -->
	<%-- <c:if test="${param.success eq true }"> --%>
	<c:if test="${success eq true }">
		<div class="alert alert-success">Registration Successful!</div>
	</c:if>

	<div class="form-group">
		<label for="name" class="col-sm-2 control-label">Name</label>
		<div class="col-sm-3">
			<form:input path="name" cssClass="form-control" />
			<!-- errors added for step 42, server side validation -->
			<form:errors path="name" />
		</div>
	</div>

	<div class="form-group">
		<label for="email" class="col-sm-2 control-label">Email</label>
		<div class="col-sm-3">
			<form:input path="email" cssClass="form-control" />
			<!-- errors added for step 42, server side validation -->
			<form:errors path="email" />
		</div>
	</div>

	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password:
		</label>
		<div class="col-sm-3">
			<form:password path="password" cssClass="form-control" />
			<!-- errors added for step 42, server side validation -->
			<form:errors path="password" />
		</div>
	</div>
	<!-- step 44, another form group added for enabling jquery validation -->
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password
			Again: </label>
		<div class="col-sm-3">
			<input type="password" name="password_again" id="password_again"
				class="form-control">
		</div>
	</div>

	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" value="Save" class="btn btn-lg btn-primary" />
		</div>
	</div>

</form:form>
<!-- Changes made to the name: section of the validation rules as part of step44.  
To test from browser, press CNTRL+SHIFT+I to see debugger.
Also add a custom message for this username check under messages section under validator piece. -->
<script type="text/javascript">
	$(document).ready(function() {
				$(".registrationForm").validate(
						{
							rules: {
								name: {
									required: true,
									minlength: 3,
									remote: {
										url: "<spring:url value='/available' />",
										type: "get",
										data: {
											userName: function(){
												return $("#name").val();
											}
										}
									}
								},
								email: {
									required: true,
									email: true,
									minlength: 10
								},
								password: {
									required: true,
									minlength: 5
								},
								password_again: {
									required: true,
									minlength: 5,
									equalTo: "#password"
								}
							},
							highlight: function(element) {
									$(element).closest('.form-group')
											.removeClass('has-success')
											.addClass('has-error');
								},
								unhighlight: function(element) {
									$(element).closest('.form-group')
											.removeClass('has-error').addClass(
													'has-success');
								},
								messages: {
									name: {
										remote: "Such username already exists!!"
									}
								}
							
						});
			});
</script>