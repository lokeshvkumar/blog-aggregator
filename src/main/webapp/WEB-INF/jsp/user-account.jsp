<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/taglib.jsp"%>

<script type="text/javascript">

<!-- step 39, added to show one active tab on load. -->
$(document).ready(function(){
	// Select first tab based on class. Observer 'nav-tabs a:first' character a used to 
	// identify first tab. Refer documentation under http://getbootstrap.com/javascript/#tabs
	$('.nav-tabs a:first').tab('show'); 
	
	/* step 41. Following lines added to trigger pop up on blog removal operation */
	$(".triggerRemove").click(function(e){
		//prevetn default avoids going to the default link which is 
		///blog/remove/${blog.id} mentioned in href for each blog
		e.preventDefault();
		//next pull the removeBtn class inside removeBlog div
		//and set the href attribute value to this (page's) object's
		//href attribute
		$("#removeBlog .removeBtn").attr("href",$(this).attr("href"));
		//next, show the modal form.
		$("#removeBlog").modal();//modal instead of show
		//repeast the same steps for users.
		
		/* added as part of step 44, form validation using jquery validation */
		
		$(".blogForm").validate(
				{
					rules: {
						name: {
							required: true,
							minlength: 3
						},
						name: {
							required: true,
							minlength: 10,
							url: true
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
						}
					}
				}		
		);
		
	});
	
	
});
</script>
<!-- step 22 is to include all taglibs in single file and include to each jsp file.
Create a jsp file named taglib.jsp and copy all directive in that single file.-->
<!-- step 24 -  Hibernate Lazy initialization 
When user information gets printed, associated blog and item information neds to be printed too.
-->
<!-- Intially you get lazy initialization exception because click on user, retrieves user information only.
Mapping of users blogs variable is by default lazy. When user gets loaded, the blogs don't get loaded.
Instead some proxy does it. When you access proxy outside transaction, then you get lazy initialization exception.
Accessing ${user.blogs} is outside the transaction. To fix this, there are two ways.
1- filter in web.xml. This will have its own implications as you wont have control over the select statement.
<filter>
    <filter-name>
        OpenEntityManagerInViewFilter
    </filter-name>
        <filter-class>
            org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter
        </filter-class>
        <init-param>
    <param-name>entityManagerFactoryBeanName</param-name>
    <param-value>entityManagerFactory</param-value>
        </init-param>
  </filter>

  <filter-mapping>
    <filter-name>OpenEntityManagerInViewFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
 
 2. to create method in service layer to retrieve user with blogs. 
 In blog repository add a method which does find by something. 

-->

<h1>${user.name }</h1>
<!-- Button trigger modal -->
<button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
	data-target="#myModal">Add Blog</button>

<br />
<br />
<!-- step 39 is to use bootstrap tabs. This was to make the user-details blogs look better. First for each blog, print  -->
<div role="tabpanel">
	<!-- Nav tabs -->
	<ul class="nav nav-tabs" role="tablist">
		<!-- firstly, for step 39 the iteration through the tabs.Next under the tab content, we need the content printed. -->
		<c:forEach items="${user.blogs}" var="blog">
			<li role="presentation"><a href="#blog_${blog.id}"
				aria-controls="profile" role="tab" data-toggle="tab">${blog.name }</a></li>
		</c:forEach>
	</ul>

	<!-- Tab panes -->
	<div class="tab-content">
		<c:forEach items="${user.blogs}" var="blog">
		<!-- update the id of the tab pane below to blog_${blog.id}. So based on selection above, corresponding content shows up. To activate one
		of the tabs when initially the content is loaded use jqeury script above. -->
			<div role="tabpanel" class="tab-pane active" id="blog_${blog.id}">
					<h1>${blog.name}</h1>
					<p>
					<!-- step 40, this href added in order to allow for blog removal. New blog did not have items, hence delete worked. But existing blog with items fails to delete. 
					rg.springframework.web.util.NestedServletException: Request processing failed; nested exception is org.springframework.orm.jpa.JpaSystemException: org.hibernate.exception.ConstraintViolationException: could not execute statement; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.ConstraintViolationException: could not execute statement
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:978)
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:857)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:687)
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:842) -->
					<a href='<spring:url value="/blog/remove/${blog.id}"/>' class="btb btn-danger triggerRemove">remove blog</a>
					
					${blog.url}</p>
					<table class="table table-bordered table-hover table-striped">
						<thead>
							<tr>
							<!-- as part of step 46, changed below headers from title and date to date and item -->
								<th>Date</th>
								<th>item</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${blog.items }" var="item">
								<tr>
								<!-- as part of step 46, changed below headers from title and date to date and item -->
									<td><c:out value="${item.publishedDate}" /></td>
									<td>
									<!-- below lines will open the link in same page. target="_blank' ensures that the
									link opens in new window -->
										<strong>
											<a href="<c:out value="${item.link}"/>" target="_blank">
												<c:out value="${item.title}"/><!-- just to avoid cross site scripting use c:out -->
											</a>
										</strong>
										<br/>
										${item.description}
										
									
										<%-- <strong>
											<a href="<c:out value="${item.link}"/>">${item.link}</a>
										</strong> --%>
									</td> 
									<%-- <td><a href="#" onclick="openLink(${item.link},'${item.link}');">${item.link}</a></td> --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
			</div>
		</c:forEach>
	</div>

</div>


<!-- Modal section was added as part of step 38 in order to allow users to be able to add blogs -->
<!-- step 38, add ModelAttribute under UserController class in order to be able to create a new Blog object on form
submission.
-->
<!-- cssClass="form-horizontal" was copied in order to have form similar to registration page -->
<form:form commandName="blog" cssClass="form-horizontal blogForm">
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Blog Details</h4>
				</div>
				<!-- the below section was copied to have form group, same as registration page. for step 38. Notice that the attributes match 
				the private instance variables of the Blog entity -->
				<div class="modal-body">
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">Name: </label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" />
							<!-- errors added for step 42, server side validation -->
							<form:errors path="name"/>
						</div>
					</div>
					<div class="form-group">
						<label for="url" class="col-sm-2 control-label">URL: </label>
						<div class="col-sm-10">
							<form:input path="url" cssClass="form-control" />
							<!-- errors added for step 42, server side validation -->
							<form:errors path="url"/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<!-- instead of the button below, input field of type text added and class copied over for css characteristics -->
					<input type="submit" class="btn btn-primary" value="Save" />
					<!-- <button type="button" class="btn btn-primary">Save changes</button> -->
				</div>
			</div>
		</div>
	</div>
</form:form>

<!-- The whole section below was commented to step 39. Instead, tabbed content was updated for this page. Refer tab contents above.-->
<%-- <c:forEach items="${user.blogs}" var="blog">
	<h1>${blog.name}</h1>
	<p>${blog.url}</p>
	<table class="table table-bordered table-hover table-striped">
		<thead>
			<tr>
				<th>Title</th>
				<th>Link</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${blog.items }" var="item">
				<tr>
					<td>${item.title}</td>
					<td>${item.link}</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>
</c:forEach> --%>

<!-- below section was added as part of step 41. To make it a bit more difficult to delete or remove something from UI and avoid accidental deletes.
 -->
<div class="modal fade" style="" id="removeBlog" tabindex="-1" role="dialog" aria-labelledby="purchaseLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="purchaseLabel">Remove Blog</h4>
                </div>
                <div class="modal-body">
                    Really wish to remove?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <a href="" class="btn btn-danger removeBtn">Remove Blog</a>
                    <!-- <button type="button" class="btn btn-primary">Purchase</button> -->
                </div>
            </div>
        </div>
    </div> 