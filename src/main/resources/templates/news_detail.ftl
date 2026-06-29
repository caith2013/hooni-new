<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>${news.title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="News: ${news.title} at www.hooni.org" />
<meta name="keywords" content="${news.title?replace(' ',',')}" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="${news.title}" />
<meta name="product-path" content="/news?nid=${news.id?c}" />
	<meta property="og:type" content="hooni.org:news"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	<meta property="og:image" content="http://www.hooni.org/news/${news.id?c}_full.jpg"/>
	<meta property="og:description" content="Hooni's news at www.hooni.org: ${news.title}"/>
	<meta property="og:title" content="${news.title}"/>
	<meta property="og:url" content="http://www.hooni.org:80/news?nid=${news.id?c}"/>
</head>
<body>
<div id="container">

		 <@mainMenu/>
		 <@HomeLeftMenu/>
			 
		 <div id="contents">
		 			<div id="contents_left">

		 					 

					<div id="news_detail">
							 <div id="news_title">${news.title}</div>
							 <div id="creator">
							 <span class="who">Created by <a href="user?u=${news.user.userName}">${news.user.userName}</a></span>
							 <span class="time">on ${news.timeCreated?string("EEEE, MMMM dd, yyyy")}</span>
							 </div>
							 
							 
							 <div id="news_contents"><#if news.numPicture != 0><img src="/news/${news.id?c}_1_full.jpg" alt="${news.title}"/></#if>${news.contents}</div>
													 
													
				</div> <!-- end of news_detail -->
				<div id="opinion">
				All the news on this board are individuals' opinions. You should take consideration carefully on all the information you get from our board.
				</div>

		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->


</div><!-- end of container -->

<@footer />

</body>
</html>
