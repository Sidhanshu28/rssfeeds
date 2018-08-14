<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>View all feeds</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value='/styles/css/style.css' />" rel="stylesheet"></link>
<link href="<c:url value='/styles/css/bootstrap.css' />"
	rel="stylesheet"></link>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js" ></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</head>
<body>
	<div class="container">
		<h1 align="center">Feeds List</h1>

		<form:form class="form-horizontal">
			<table
				class="table table-bordered table-hovered table-striped viewfeeds"
				style="text-align:center">
				<thead>
					<tr>
						<th>Id</th>
						<th>Feed Name</th>
						<th>Feed URL</th>
						<th>Feed Title</th>
						<th>Last updated</th>
						<th>View Feed</th>
						<th>Delete Feed</th>
					</tr>
				</thead>
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
			<br />


		</form:form>
		<br>
		<br>
		<div class="addfeed">
			<form:form method="POST" name="loginform" modelAttribute="feeds" action="save" onsubmit="return validate(event);">

				<div class="form-group row justify-content-md-center">
					<label for="FeedName" class="col-lg-2 col-form-label text-md-right">Feed
						Name :</label>
					<div class="col-lg-4">
						<form:input type="text" path="FeedName" name="feedname" id="FeedName"
							class="form-control" placeholder="Enter Feed Name" />
					</div>
				</div>

				<div class="form-group row justify-content-md-center">
					<label for="feedUrl" class="col-lg-2 col-form-label text-md-right">Feed
						URL :</label>
					<div class="col-lg-4">
						<form:input type="text" name="feedurl" path="feedUrl" id="feedUrl"
							class="form-control" placeholder="https://www.example.com" />
					</div>
				</div>

				<div class="form-group row justify-content-md-center">
					<div class="col-lg-2">
						<input type="submit" value="Add Feed" class="btn btn-primary" >
					</div>
				</div>



			</form:form>
		</div>
	</div>
<script type="text/javascript" async>
            function validate(e)
            {
            	 e.preventDefault(); // this will prevent the submit event.
            	 var feedname = document.loginform.FeedName.value;
            	 var feedurl = document.loginform.feedUrl.value;
            	 var regexQuery = "^(https?://)?(www\\.)?([-a-z0-9]{1,63}\\.)*?[a-z0-9][-a-z0-9]{0,61}[a-z0-9]\\.[a-z]{2,6}(/[-\\w@\\+\\.~#\\?&/=%]*)?$";
            	 var url = new RegExp(regexQuery,"i");
            	 var isUrl = url.test(feedurl);
            	var flag = true;
            	 try{
                	 var isValid = new URL(feedurl);
            	 }
            	 catch(e){
            		flag = false;
            	 }
            	 
            	    if(feedname=="")
            	    {
            	      alert("Feed Name can not be left blank");
            	      document.loginform.FeedName.focus();
            	      return false;
            	    }
            	    else if(feedurl=="")
            	    {
            	      alert("Feed Url can not be left blank");
            	      document.loginform.feedUrl.focus();
            	      return false;
            	    }
            	    else if(!isUrl || !flag){
                    	 alert("Inncorrect url");
                	      document.loginform.feedUrl.focus();
                	      return false;
            	    }
            	    else {
            	        document.loginform.submit(); // fire submit event
            	    }
            	    
            };
</script>
<script type="text/javascript">
(function($) {
    var $window = $(window),
        $html = $('html');

    $window.resize(function resize(){
    	console.log($window.width(),$('.viewfeeds').width());
        if ($window.width() < 768) {
        	
            return $('.viewfeeds').addClass('table-responsive');
        }

        $('.viewfeeds').removeClass('table-responsive');
    }).trigger('resize');
})(jQuery);
</script>
</body>
</html>
