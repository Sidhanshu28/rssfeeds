<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<html>
<head>
<title>View all feeds</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<c:url value='/styles/css/style.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/styles/css/bootstrap.css' />" rel="stylesheet"></link>
	<script src="/styles/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
<h1 align="center">Feeds List</h1>
   
<form:form  class="form-horizontal" >
<table class="table table-bordered table-hovered table-striped viewfeeds" style="width:100%">
<thead>
<tr><th>Id</th><th>Feed Name</th><th>Feed URL</th>
<th>Feed Title</th><th>Last updated</th>
<th>View Feed</th><th>Delete Feed</th></tr>  </thead>
<tbody>
   <c:forEach var="feed" items="${list}"> 
   <tr>  
   <td>${feed.feedId}</td>  
   <td>${feed.feedName}</td> 
   <td>${feed.feedUrl}</td>  
   <td>${feed.feedTitle}</td> 
   <td>${feed.lastUpdated}</td>  
   <td><a href="/rssfeeds/viewFeed/${feed.feedId}">View</a></td>  
   <td><a href="/rssfeeds/deletefeed/${feed.feedId}">Delete</a></td>  
   </tr>  
   </c:forEach> 
   
   </tbody>
   </table>  
   <br/>
   
  
   </form:form>
   <br><br>
   <div class="addfeed">
		<form:form method="POST" modelAttribute="feeds"  action="save">
				
				<div class="form-group row justify-content-md-center">
					<label for="FeedName" class="col-lg-2 col-form-label text-md-right">Feed Name :</label>
					<div class="col-lg-4">
						<form:input type="text" path="FeedName"  id="FeedName" class="form-control" placeholder="Enter Feed Name"/>					
					</div>
				</div>
				
				<div class="form-group row justify-content-md-center">
					<label  for="feedUrl" class="col-lg-2 col-form-label text-md-right" >Feed URL :</label>
					<div class="col-lg-4">
						<form:input type="text" path="feedUrl" id="feedUrl" class="form-control" placeholder="Enter Feed URL"/>
					</div>
				</div>
				
				<div class="form-group row justify-content-md-center">
					<div class="col-lg-2">
						<input type="submit" value="Add Feed"  class="btn btn-primary">
					</div>
				</div>
				
				
				
		</form:form>
	</div>
</div>

</body>
</html>