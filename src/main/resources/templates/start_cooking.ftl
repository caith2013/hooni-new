<#include "macros.macro" />
<#include "food.macro" />


<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Foods: Sharing your tastes</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/food_styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<link href="/hooni/css/jquery-ui-1.8.23.custom.css" rel="stylesheet" type="text/css"/>



<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/hooni/js/food_search.js"></script>
<script type="text/javascript" src="/hooni/js/food_plan.js"></script>
<script type="text/javascript" src="/hooni/js/popup.js"></script><br />
<script type="text/javascript" src="/hooni/js/zoom_food.js"></script>


<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="A list of recipes from hooni.org" />
<meta name="keywords" content="hooni, foods,food,recipe,recipes,dish,cooking,vegetables,meats,ingredients" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="Hooni: Sharing your tastes" />
<meta name="product-path" content="/food" />
	<meta property="og:type" content="cooking foods"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	
	<meta property="og:description" content="A list of dishes from hooni.org"/>
	<meta property="og:title" content="Sharing your tastes"/>
	<meta property="og:url" content="http://food.hooni.org:80"/>
	
	
</head>
<body>
<div id="container">

		 <@FoodMainHeader/>
		
<div id="left_menu">	
		 <@FoodLeftMenu/>
		 <div id="specials">
					 <div class="sp_header">Today's Specials</div>
					 <#list todayspecials as ts>
					 <div class="sp_item">
					 			<div class="type_header"><a href="/foods?fid=${ts.id?c}">Today's ${foodType[ts.kind]?lower_case?cap_first} special</a></div>
					 			<div class="thumb"><a href="/foods?fid=${ts.id?c}"><img src="/foods/${ts.id?c}_thumb.jpg" alt="${ts.title?string}" /></a></div>
								<div class="desc"><span class="title"><a href="/foods?fid=${ts.id?c}">${ts.title?string}</a></span></div>
					 			
					 </div>
					 </#list>
			</div><!-- end of specials -->
</div><!-- end of left_menu --> 
		 
		 <div id="contents">
		 			<div id="contents_left">
					<#if breakfast??>
					
					<#list breakfast as bf>
						 <div id="food_detail">
				
						 			<div class="detail_section">
											 
						 					 <div id="food_title" class="food_title_font"> ${bf.title}</div>
											  
											 <div id="food_maker">By <a href="#">${bf.user.userName}</a> on ${bf.timeCreated?string("EEEE, MMMM dd, yyyy")}</div>
											 
						 			</div>
									
									<div class="detail_section">
						 					 <div id="detail_left">
    											 <div id="food_images">
    						 					 			<img src="http://${hosts.imageServer}:${hosts.imagePort}/foods/${bf.id?c}_full.jpg" alt="${bf.title}" id="full_img"/>
															
    											 </div><!-- end of food_images -->
											 			
														
														
											</div><!-- end of detail_left -->
											<div id="food_desc">
										
													 <div class="food_description">${bf.description}</div>
													<ol>
												
															
															<#list bf.foodSteps as foodStep>
															<#if foodStep.hasPicture >
											 				<li class="with_image"><img src="/foods/${bf.id?c}_${foodStep.id?c}_thumb.jpg" alt="" class="thumb"/>
															<#else>
															<li>
															</#if>${foodStep.description}</li>
											 				</#list>
															
													</ol>
													 
													 		  
													 
											</div>
											 
						 			</div>
									
									
						
						 
				</div><!-- end of food_detail -->
			</#list>		
			</#if>
			
			
			
			
			<#if lunch??>
					 
					<#list lunch as lu>
						 <div id="food_detail">
				
						 			<div class="detail_section">
											 
						 					 <div id="food_title" class="food_title_font"> ${lu.title}</div>
											  
											 <div id="food_maker">By <a href="#">${lu.user.userName}</a> on ${lu.timeCreated?string("EEEE, MMMM dd, yyyy")}</div>
											 
						 			</div>
									
									<div class="detail_section">
						 					 <div id="detail_left">
    											 <div id="food_images">
    						 					 			<img src="http://${hosts.imageServer}:${hosts.imagePort}/foods/${lu.id?c}_full.jpg" alt="${lu.title}" id="full_img"/>
															
    											 </div><!-- end of food_images -->
											 			
														
														
											</div><!-- end of detail_left -->
											<div id="food_desc">
										
													 <div class="food_description">${lu.description}</div>
													<ol>
												
															
															<#list lu.foodSteps as foodStep>
															<#if foodStep.hasPicture >
											 				<li class="with_image"><img src="/foods/${lu.id?c}_${foodStep.id?c}_thumb.jpg" alt="" class="thumb"/>
															<#else>
															<li>
															</#if>${foodStep.description}</li>
											 				</#list>
															
													</ol>
													 
													 		  
													 
											</div>
											 
						 			</div>
									
									
						
						 
				</div><!-- end of food_detail -->
			</#list>		
			</#if>
			
			<#if dinner??>
				
					<#list dinner as di>
						 <div id="food_detail">
				
						 			<div class="detail_section">
											 
						 					 <div id="food_title" class="food_title_font"> ${di.title}</div>
											  
											 <div id="food_maker">By <a href="#">${di.user.userName}</a> on ${di.timeCreated?string("EEEE, MMMM dd, yyyy")}</div>
											 
						 			</div>
									
									<div class="detail_section">
						 					 <div id="detail_left">
    											 <div id="food_images">
    						 					 			<img src="http://${hosts.imageServer}:${hosts.imagePort}/foods/${di.id?c}_full.jpg" alt="${di.title}" id="full_img"/>
															
    											 </div><!-- end of food_images -->
											 			
														
														
											</div><!-- end of detail_left -->
											<div id="food_desc">
										
													 <div class="food_description">${di.description}</div>
													<ol>
												
															
															<#list di.foodSteps as foodStep>
															<#if foodStep.hasPicture >
											 				<li class="with_image"><img src="/foods/${di.id?c}_${foodStep.id?c}_thumb.jpg" alt="" class="thumb"/>
															<#else>
															<li>
															</#if>${foodStep.description}</li>
											 				</#list>
															
													</ol>
													 
													 		  
													 
											</div>
											 
						 			</div>
									
									
						
						 
				</div><!-- end of food_detail -->
			</#list>		
			</#if>
							
					
		</div><!-- end of contents_left -->
		
	<div id="contents_right">
						  <@JoinForFree />
							
	
	
	<div id="my_ad">
					 <div class="ad_item">
					 		Your ad here
					 </div>
					 <div class="ad_item">
					 		Your ad here
					 </div>
					 <div class="ad_item">
					 		Your ad here
					 </div>
					 
	</div>					
				
		</div><!-- end of contents_right -->
		
		</div><!-- end of contents -->



</div><!-- end of container -->

 <@footer />

</body>
</html>
