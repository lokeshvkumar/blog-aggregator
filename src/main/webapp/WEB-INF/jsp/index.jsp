<!DOCTYPE html>
<!-- saved from url=(0035)http://gadole.com/guideu/modal.html -->
<html>
<title>Blog Index</title>
<head>
<h1>Latest news from the world of Java</h1>
<%@ include file="../layout/taglib.jsp" %>
<!-- As part of step 46, added content to index.jsp to show feeds.
ItemService added to pull all items from database. -->
</head>
					<table class="table table-bordered table-hover table-striped">
						<thead>
							<tr>
							<!-- as part of step 46, changed below headers from title and date to date and item -->
								<th>Date</th>
								<th>item</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${items }" var="item">
								<tr>
								<!-- as part of step 46, changed below headers from title and date to date and item -->
									<td><c:out value="${item.publishedDate}" />
									<c:out value="${item.blog.name}" /></td>
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
										
									</td> 
								</tr>
							</c:forEach>
						</tbody>
					</table>
</html>