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
	<h1 align="center">Feeds List</h1>

	<form:form class="form-horizontal">
		<table
			class="table table-bordered table-hovered table-striped viewfeedbyid"
			style="width:75%">
			<thead>
				<tr>
					<th colspan="4">RSS Feed Details</th>
				</tr>
				<tr>
					<th>Feed Name</th>
					<th>Feed Url</th>
					<th>Feed Update Date and Time</th>
					<th>Article Count</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="feed" begin="1" end="1" items="${list}">
					<tr>
						<td>${feed.feedName}</td>
						<td>${feed.feedUrl}</td>
						<td>${feed.lastUpdated}</td>
						<td>${feed.feedArticleCount}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
		<br />


		<table
			class="table table-bordered table-hovered table-striped viewfeedbyid"
			style="width: 75%">
			<thead>
				<tr>
					<th colspan="2">Feed's Latest Articles</th>
				</tr>
				<tr>
					<th>Item Title</th>
					<th>Item Link</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${list}">
					<tr>
						<td>${item.itemTitle}</td>
						<td>${item.itemLink}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
		<br />
		<center>
			<a href="/rssfeeds/viewFeeds"><input type="button" value="Back"
				class="btn btn-primary btn-sm"></a>
		</center>
	</form:form>
	<script type="text/javascript">
(function($) {
    var $window = $(window),
        $html = $('html');

    $window.resize(function resize(){
    	console.log($window.width(),$('.viewfeedbyid').width());
        if ($window.width() < 768) {
        	
            return $('.viewfeedbyid').addClass('table-responsive');
        }

        $('.viewfeedbyid').removeClass('table-responsive');
    }).trigger('resize');
})(jQuery);
</script>
</body>
</html>
