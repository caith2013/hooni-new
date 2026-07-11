<#include "/include/macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hooni: new techs make life easier</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/css/styles.css" />
<!--[if IE]><style>@import url("/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/homepage.js"></script>
</head>
<body>
<div id="container">
 <@HomeLeftMenu/>
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

                                                                            <a href="/news?nid=${n.id?c}"><#if n.numPicture != 0><img src="/images/news/${n.id?c}_1_mini.jpg" alt=""/></#if>${n.title}</a>
																	
															</li>
															<#if n_index = 30><li><dl><dt><a href="/www?op=news">More...</a></dt></dl></li><#break></#if>
															</#list>
												 </ul>
										</div>
										
										<div class="sub_item">
												 <div class="sub_title"><div class="subject"><a href="/www?op=products">Products...</a></div><div class="definition">Products make life easier</div></div>
												 <#if products??>
												 <ul>
											
												 		  <#list products as p>
															<li>
																	<dl>
																			<dt><a href="/product?pid=${p.id?c}"><img src="/images/products/${p.id?c}_thumb.jpg" alt=""></a></dt>
																			<dd>${p.title?substring(0, 20)}</dd>
																			<dd><span class="cross_price">${p.price?string.currency}</span>&nbsp;&nbsp;<span class="price_color">${p.finalPrice?string.currency}</span></dd>
																	</dl>
															</li>
															<#if p_index = 9><li><dl><dt><a href="/www?op=products">More...</a></dt></dl></li><#break></#if>
															</#list>
												 </ul>
												 </#if>
										</div>
										
										
										<div class="sub_item">
												 <div class="sub_title"><div class="subject"><a href="/www?op=foods">Foods...</a></div><div class="definition">Share your taste with others</div></div>
												 <#if foods??>
												 <ul>
												 		  <#list foods as f>
															<li>
																	<dl>
																			<dt><a href="/foods?fid=${f.id?c}"><img src="/images/foods/${f.id?c}_thumb.jpg" alt=""></a></dt>
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
															<#if ad_index = 30><li><dl><dt><a href="/www?op=ads">More...</a></dt></dl></li><#break></#if>
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
															<#if s_index = 30><li><dl><dt><a href="/www?op=shares">More...</a></dt></dl></li><#break></#if>
															</#list>
												 </ul>
												 </#if>
										</div>
										
										<div class="sub_item">
												  <div class="sub_title"><div class="subject"></div><div class="definition"></div></div>
													<ul>
															<li>
															<div id="shopping_cart">
																	 this is a test message
															</div><!-- end of shopping_cart -->
															</li>
															<li>
																	 <div id="my_ad">
					 												 			<div class="ad_item">
					 		
					 															</div>
																	 </div>
															
															</li>
													</ul>
										</div>
										
							 </div><!-- end of sub_container -->
							 
							
							 
			 		</div><!-- end of contents_left -->
		 			
				
					<@footer />
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
