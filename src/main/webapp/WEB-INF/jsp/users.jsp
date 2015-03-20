<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/taglib.jsp"%>
<!-- step 23 This page includes the jstl core tag library. Simply iterate through the users list
using for each loop and print the item. Also using bootstrap stylesheet class for table shown below.
-->

<script type="text/javascript">
<!-- The below script added in order to avoid accidental delete of users.For explanation refer same piece of code in user-details.jsp -->
	
	$(document).ready(function(){
			$(".triggerRemove").click(function(e){
				e.preventDefault();
				$("#removeUser .removeBtn").attr("href",$(this).attr("href"));
				$("#removeUser").modal();
			});//end$(".trigger")
	});//$(document).ready
					
</script>


<table class="table table-bordered table-hover table-striped">
	<thead>
		<tr>
			<th>User Name</th>
			<th>Operations</th>
		</tr>


	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<!-- Click on each user can navigate to a different page itself. This can be acomplished by adding alink
			Also spring tag libs can be sued for the same -->
				<td><a
					href="<spring:url value="/users/${user.id}"></spring:url>" />
					<!-- step 42, using jstl tag for output value will ensure 
					that any scripting input will not be interpreted and will be output as plain
					text input -->
					<c:out value="${user.name}"/>
				</td>
				<td><a href='<spring:url value="/users/remove/${user.id }"/>'
					class="btn btn-danger triggerRemove">remove</a></td>
			</tr>
		</c:forEach>
	</tbody>
	
</table>

<!-- below section was added as part of step 41. To make it a bit more difficult to delete or remove users from
	UI and avoid accidental deletes.
 -->
	<div class="modal fade" style="" id="removeUser" tabindex="-1"
		role="dialog" aria-labelledby="purchaseLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="purchaseLabel">Remove User</h4>
				</div>
				<div class="modal-body">Really wish to remove the User?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<a href="" class="btn btn-danger removeBtn">Remove User</a>
					<!-- <button type="button" class="btn btn-primary">Purchase</button> -->
				</div>
			</div>
		</div>
	</div>