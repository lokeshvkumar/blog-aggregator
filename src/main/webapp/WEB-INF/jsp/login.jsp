<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<style>
.form-signin {
	max-width: 330px;
	padding: 15px;
	margin: 0 auto;
}

.form-signin .form-signin-heading,.form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin .checkbox {
	font-weight: normal;
}

.form-signin .form-control {
	position: relative;
	height: auto;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	padding: 10px;
	font-size: 16px;
}

.form-signin .form-control:focus {
	z-index: 2;
}

.form-signin input[type="email"] {
	margin-bottom: -1px;
	border-bottom-right-radius: 0;
	border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
	margin-bottom: 10px;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
</style>
<!-- for acton value, copy the content from default security login page source code -->
<!-- Two additional attributes added to the input tags, name="j_username" name="j_password" -->
<form class="form-signin" method="POST" role="form" action="/j_spring_security_check">
	<h2 class="form-signin-heading">Please sign in</h2>
	<label for="name" class="sr-only">Username:</label> 
	<input type="text" name="j_username" id="name" class="form-control" placeholder="name" required autofocus> 
	<label for="password" class="sr-only">Password:</label> 
	<input type="password" name="j_password" id="password" class="form-control" placeholder="password" required>
	<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
</form>