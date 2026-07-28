<#include "/include/macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Share your expertise and offer services</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/css/styles.css" />
<!--[if IE]><style>@import url("/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/js/jquery.js"></script>

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="A list of blogs from hooni.org" />
<meta name="keywords" content="share,expertise,offer,blog,story,howto,service,web,internet,solution" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="Share your expertise and offer services" />
<meta name="product-path" content="/www?op=shares" />
	<meta property="og:type" content="blog,share,information,service"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	
	<meta property="og:description" content="A list of blogs from hooni.org"/>
	<meta property="og:title" content="Share your expertise and offer services"/>
	<meta property="og:url" content="http://www.hooni.org:80/www?op=shares"/>
</head>
<body>
<div id="container">

		 <@mainMenu/>
		  <@HomeLeftMenu/>
		 
		 <div id="contents">
		 			<div id="contents_left">


		 					 <div class="page_title">Welcome...It is nice to see you here.</div>
							 <ul>
							 <#list HooniItems as s>
							 				<li style="padding: 5px;"><a href="/share/${s.id?c}">${s.title}...<#if s.price == 0>Free<#else>${s.price?string.currency}/${s.byRate}</#if></a></li>
								
							
							 </#list>
							 </ul>
							 
							 <@footer />
							 
			 		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
