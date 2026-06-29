<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="Sharing Latest news from the regular people" />
<meta name="keywords" content="sharing,news,people, events, thoughts,new teches, technologies,local, web, internet, hang out" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="Hooni: Knowing the world around you" />
<meta name="product-path" content="/www?op=news" />
	<meta property="og:type" content="Sharing the news around you"/>
	<meta property="og:site_name" content="www.hooni.org"/>

	<meta property="og:description" content="www.hooni.org - Latest news"/>
	<meta property="og:title" content="Hooni.org: Knowing the world around you"/>
	<meta property="og:url" content="http://www.hooni.org:80/www?op=news"/>
	
	
<head>
<title>Hooni: Knowing the world around you</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
</head>
<body>
<div id="container">

		 <@mainMenu/>
		 <@HomeLeftMenu/>
		 
		 <div id="contents">
		 			<div id="contents_left">


		 					 <div class="page_title">Welcome...It is nice to see you here.</div>
		 			  <div id="all_news">
		 					 <div id="general_news">
		 					 	<ul class="news_home">
							 	<#list HooniItems as n>

												<li style="padding: 5px;"><a href="/news?nid=${n.id?c}"><#if n.numPicture != 0><img src="/news/${n.id?c}_1_mini.jpg" alt=""/></#if>${n.title}</a>

																			
																	
												</li>
								</#list>
								</ul>
							</div>
							<div id="local_news">
								<span id="local_place"><a href="/news?city=${your_city_location!?c}">${your_city!}</a></span>
								<ul>
									
								</ul>
							</div>
					</div>
					
					<@footer />
					
			 		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
