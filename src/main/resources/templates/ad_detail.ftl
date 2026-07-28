<#include "include/macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>${ad.subject}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/css/styles.css" />
<!--[if IE]><style>@import url("/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/js/jquery.js"></script>

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="Ads: ${ad.subject}" />
<meta name="keywords" content="${ad.adcat.name},<#list ad.adKeywords as adKey>${adKey.keyword}<#if adKey_has_next>,</#if></#list>" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="${ad.subject}" />
<meta name="product-path" content="/ads?aid=${ad.id?c}" />
	<meta property="og:type" content="ads"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	
	<meta property="og:description" content="${ad.subject}"/>
	<meta property="og:title" content="${ad.subject}"/>
	<meta property="og:url" content="http://www.hooni.org:80/ads?aid=${ad.id?c}"/>
	
</head>
<body>
<div id="container">

		 <@mainMenu/>
		 <@HomeLeftMenu/>
			 
		 <div id="contents">
		 			<div id="contents_left">

		 					 

					<div id="ads_detail">
							 <div id="key_words">
							 			<ul>
												<li><a href="/keywords?cat=${ad.adcat.name}">${ad.adcat.name}-->></a></li>
												<#list ad.adKeywords as adKey>
															 <li><a href="/keywords?k=${adKey.keyword}">${adKey.keyword}</a></li>
												</#list>
											
										</ul>
							 </div>
							 <div id="ads_title">${ad.subject}</div>
							 <span>By <a href="user?u=${ad.user.userName}">${ad.user.userName}</a> on ${ad.timeCreated}</span>
							 
							 <div id="ads_contents">${ad.description}</div>
						
						 <div id="ads_contact">
						 			<div id="ads_contact_label">Contact Information:</div>
									<ul>
											<li>${ad.contactInfor}</li>
											<#if ad.personal?string == "true"><li>${ad.user.email}</li></#if>
									</ul>
						 			
						 			
						 </div>
						 
						<div id="ad_reply">				
						 <form name="ad_reply_form" action="adsreply" method="get">
						 <input type="hidden" name="ad_id" value="${ad.id?c}">
						
							<input type="submit" name="submit" value="Reply">
														 
						 </form>
						</div><!-- ad_reply end here -->
												
				</div> <!-- end of ads_detail -->
							 
					<@footer />
					
		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->


</div><!-- end of container -->



</body>
</html>
