<#include "include/macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="Sharing information about new technologies, Latest news, Latest Advertisments, and Cooking skills in your community" />
<meta name="keywords" content="sharing,new teches, technologies, news, local, ads, advertisment, foods, cooking, web, internet, howto, hang out" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="www.hooni.org - Sharing new technologies, Latest news, Latest Advertisments, and Cooking skills in your community" />
<meta name="product-path" content="/www?op=hooni" />
	<meta property="og:type" content="Sharing"/>
	<meta property="og:site_name" content="www.hooni.org"/>

	<meta property="og:description" content="www.hooni.org - Sharing new technologies, Latest news, Latest Advertisments, and Cooking skills in your community"/>
	<meta property="og:title" content="www.hooni.org - Sharing new technologies, Latest news, Latest Advertisments, and Cooking skills in your community"/>
	<meta property="og:url" content="http://www.hooni.org:80"/>
	
<head>
<title>Hooni: Sharing new technologies, Latest news, Latest Advertisments, and Cooking skills</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/homepage.js"></script>
</head>
<body>
<div id="container">

		 <@mainMenu/>
		
		 
		 <div id="contents">
		 			<div id="contents_left">


		 					 <div class="page_title">Welcome...It is nice to see you here.</div>
							 <div id="sub_container">
							 			<div class="sub_item">
												 <div class="sub_title"><div class="subject"><a href="/www?op=news">News...</a></div><div class="definition">Knowing the world around you</div></div>
												 <ul>
												 		  <#list news as n>
															<li>
																	
																			<a href="/news?nid=${n.id?c}"><#if n.numPicture != 0><img src="/news/${n.id?c}_1_mini.jpg" alt=""/></#if>${n.title}</a>
																			
																	
															</li>
															<#if n_index = 30><li><a href="/www?op=news">More...</a></li><#break></#if>
															</#list>
												 </ul>
										</div>
										
										<div class="sub_item">
												 <div class="sub_title"><div class="subject"><a href="/www?op=products">Products...</a></div><div class="definition">Products to make life easier</div></div>
												 <#if products??>
												 <ul>
											
												 		  <#list products as p>
															<li>
																	<dl>
																			<dt><a href="/product?pid=${p.id?c}"><img src="/products/${p.id?c}_thumb.jpg" alt=""></a></dt>
																			<dd>${p.title?substring(0, 20)}</dd>
																			<dd><span class="cross_price">${p.price?string.currency}</span>&nbsp;&nbsp;<span class="price_color">${p.finalPrice?string.currency}</span></dd>
																	</dl>
															</li>
															<#if p_index = 9><li><a href="/www?op=products">More...</a></li><#break></#if>
															</#list>
												 </ul>
												 </#if>
										</div>
										
										
										<div class="sub_item">
												 <div class="sub_title"><div class="subject"><a href="http://food.hooni.org/">Foods...</a></div><div class="definition">Share your taste with others</div></div>
												 <#if foods??>
												 <ul>
												 		  <#list foods as f>
															<li>
																	<dl>
																			<dt><a href="/foods?fid=${f.id?c}"><img src="/foods/${f.id?c}_thumb.jpg" alt=""></a></dt>
																			<dd>${f.title}</dd>
																			
																	</dl>
															</li>
															<#if f_index = 9><li><dl><dt><a href="/www?op=foods">More...</a></dt></dl></li><#break></#if>
															</#list>
												 </ul>
												 </#if>
										</div>
										
										
										
										<div class="sub_item">
												 <div class="sub_title"><div class="subject"><a href="/www?op=ads">Ads...</a></div><div class="definition">Tell what you needs</div></div>
												 <#if ads??>
												 <ul>
												 		  <#list ads as ad>
															<li>
																	
																			<a href="/ads?aid=${ad.id?c}">${ad.subject}</a>
																			
																	
															</li>
															<#if ad_index = 30><li><a href="/www?op=ads">More...</a></li><#break></#if>
															</#list>
												 </ul>
												 </#if>
										</div>
										
										
										
										<div class="sub_item">
												  <div class="sub_title"><div class="subject"><a href="/www?op=shares">Shares...</a></div><div class="definition">Share your expertise</div></div>
													<#if shares??>
													<ul>
												 		  <#list shares as s>
															<li>
																	
																			<a href="/share?sid=${s.id?c}">${s.title}...<#if s.price == 0>Free<#else>${s.price?string.currency}/${s.byRate}</#if></a>
																			
																	
															</li>
															<#if s_index = 30><li><a href="/www?op=shares">More...</a></li><#break></#if>
															</#list>
												 </ul>
												 </#if>
										</div>
										
							 </div><!-- end of sub_container -->
							 
							
							 
			 		</div><!-- end of contents_left -->
		 			<@footer />
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
