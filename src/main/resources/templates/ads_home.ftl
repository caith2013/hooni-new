<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Advertisments on the internet</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="A list of advertisments from hooni.org" />
<meta name="keywords" content="ads,advertisments,internet,web,online,<#assign keys = adsByCat?keys> <#list keys as key>${key}<#if key_has_next>,</#if></#list>" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="Tell what you needs, put an ad on the internet" />
<meta name="product-path" content="/www?op=ads" />
	<meta property="og:type" content="ads"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	
	<meta property="og:description" content="A list of advertisments's categories from hooni.org"/>
	<meta property="og:title" content="Put an ad on the internet"/>
	<meta property="og:url" content="http://www.hooni.org:80/www?op=foods"/>
	
</head>
<body>
<div id="container">

		 <@mainMenu/>
		 <@HomeLeftMenu/>
		 
		 <div id="contents">
		 			<div id="contents_left">


		 					 <div class="page_title">Welcome...It is nice to see you here.</div>
							 <div id="all_ads">
							 <#assign nums = 0 >
							 <#assign col = 0 >
							 <#assign keys = adsByCat?keys>
							 <div class="col0">
							 <#list keys as key>
							 						 		<#assign keywords = adsByCat[key]>
															<#if keywords?size != 0>
															
															
															<ul class="ad_cat" ><span class="ad_cat_header">${key}</span>
															<#list keywords as kw>
																		 <#assign nums = nums + 1>
																		 <#if (nums >= max)> 
																		 			<#assign col = col +1>
																					<#assign nums = 0>
																					</ul>
																					</div>
																					<div class="col${col}">
																					<ul class="ad_cat" ><span class="ad_cat_header">${key}</span>
																		  </#if>
																			<li><a href="/keywords?k=${kw}">${kw}</a></li>
															</#list>
															</ul>
															
															</#if>
														
							 </#list>
							</div>
							
							</div><!-- end of all_ads -->
							
							<@footer />
							
			 		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
