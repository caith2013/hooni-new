<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>${food.title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->


<style>
#view {background-image: url( "http://${hosts.imageServer}:${hosts.imagePort}/foods/${food.id?c}.jpg" );}
</style>

<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/popup.js"></script><br />
<script type="text/javascript" src="/hooni/js/zoom_food.js"></script>

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="Cooking: ${food.title} at www.hooni.org - ${food.description?replace('<li>','')?replace('</li>','')}" />
<meta name="keywords" content="<#list food.foodKeywords as key>${key.keyword?string}<#if key_has_next>,</#if></#list>" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="${food.title}" />
<meta name="product-path" content="/foods?fid=${food.id?c}" />
	<meta property="og:type" content="foods:cooking"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	<meta property="og:image" content="http://www.hooni.org/foods/${food.id?c}_full.jpg"/>
	<meta property="og:description" content="Hooni's foods at www.hooni.org - ${food.description?replace('<li>','')?replace('</li>','')}"/>
	<meta property="og:title" content="${food.title}"/>
	<meta property="og:url" content="http://www.hooni.org:80/foods?fid=${food.id?c}"/>
</head>
<body>
<!--
<form id="iposition">
								<div id="fposition">Full::left:<input id="fleft" value="" type="text"/>top:<input id="ftop" value="" type="text"/></div>
								
								<div id="oposition">Zoom::left:<input id="zleft" value="" type="text"/>top:<input id="ztop" value="" type="text"/></div>
								<div id="oposition">zoom position::left:<input id="zpleft" value="" type="text"/>top:<input id="zptop" value="" type="text"/></div>
								<div id="oposition">after::left:<input id="afleft" value="" type="text"/>top:<input id="aftop" value="" type="text"/></div>
								
					</form>
-->
<div id="container">

		 <@mainMenu/>
		<@HomeLeftMenu/>
		 
		 <div id="contents">
		 			<div id="contents_left">

		 					 

					<div id="food_detail">
				
						 			<div class="detail_section">
						 					 <div id="food_title" class="food_title_font">${food.title} </div>
											  
											 <div id="food_maker">By <a href="#">${food.user.userName}</a> on ${food.timeCreated?string("EEEE, MMMM dd, yyyy")}</div>
											 
						 			</div>
									
									<div class="detail_section">
						 					 
											 <div id="food_images">
						 					 			<img src="http://${hosts.imageServer}:${hosts.imagePort}/foods/${food.id?c}_full.jpg" alt="${food.title}" />
														<a id='zoom'><span><br /></span></a>
						 					 </div><!-- end of food_images -->
											
											<div id="food_desc">
											<div id="view"></div>
													 <div class="food_description">${food.description}</div>
													<ol>
												
															<#list food.foodSteps as foodStep>
															<#if foodStep.hasPicture >
											 				<li class="with_image"><img src="/foods/${food.id?c}_${foodStep.id?c}_thumb.jpg" alt="" class="thumb"/>
															<#else>
															<li>
															</#if>${foodStep.description}</li>
											 				</#list>
															
													</ol>
													 
													 		  
													 
											</div>
											 
						 			</div>
									
									
						
						 
				</div><!-- end of food_detail -->
							
														
				
				<@footer />

		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->


</div><!-- end of container -->

<div id="popup"><img src="/images/key.png" alt="" id="popup_img"/></div>



</body>
</html>
