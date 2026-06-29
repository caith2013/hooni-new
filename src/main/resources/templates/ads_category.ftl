<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hooni: new techs make life easier</title>
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
		 					 <#if ads??>
							 <ul class="news_home">
							 
							 <#list ads as ad>

												<li style="padding: 5px;"><a href="/ads?aid=${ad.id?c}">${ad.subject}</a>
												</li>
							</#list>
							</ul>
							</#if>
							
							
							<@footer />
							
							
			 		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
