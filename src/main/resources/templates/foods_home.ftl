<#include "include/macros.macro" />
<#include "include/food.macro" />


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

<style>
#view {background-image: url( "http://${hosts.imageServer}:${hosts.imagePort}/foods/${theday.id?c}.jpg" );}
</style>

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
						 <div id="food_detail">
				
						 			<div class="detail_section">
											 <div id="makemyday">Recipe of the day</div>
						 					 <div id="food_title" class="food_title_font"> ${theday.title}</div>
											  
											 <div id="food_maker">By <a href="#">${theday.user.userName}</a> on ${theday.timeCreated?string("EEEE, MMMM dd, yyyy")}</div>
											 
						 			</div>
									
									<div class="detail_section">
						 					 <div id="detail_left">
    											 <div id="food_images">
    						 					 			<img src="http://${hosts.imageServer}:${hosts.imagePort}/foods/${theday.id?c}_full.jpg" alt="${theday.title}" id="full_img"/>
																<a id='zoom'><span><br /></span></a>
    											 </div><!-- end of food_images -->
											 			
														
														<div id="zoom_text">Rollover To Zoom&nbsp;<span class="zoom_icon"><img src="/images/zoom.jpg" alt="Rollover To Zoom" /></span></div>
														<div id="addto">
																 <#list theday.foodGoodFor as mt>
														<a href="javascript:void(0)" class="addto" fid="${theday.id?c}" title="${theday.title?string}" mealtype="${mt.mealType?c}">Add it as ${mealType[mt.mealType]?lower_case}</a>
														</#list>
														</div>
														<div id="more_and_favor">
																 <a href="javascript:void(0)" class="morelike" foodtype="${theday.kind?c}">More Recipes Like This</a>
																 <a href="javascript:void(0)" class="favorite" fid="${theday.id?c}" title="${theday.title?string}">Add as your favorite</a>
														</div>
											</div><!-- end of detail_left -->
											<div id="food_desc">
											<div id="view"></div>
													 <div class="food_description">${theday.description}</div>
													<ol>
												
															
															<#list theday.foodSteps as foodStep>
															<#if foodStep.hasPicture >
											 				<li class="with_image"><img src="/foods/${theday.id?c}_${foodStep.id?c}_thumb.jpg" alt="" class="thumb"/>
															<#else>
															<li>
															</#if>${foodStep.description}</li>
											 				</#list>
															
													</ol>
													 
													 		  
													 
											</div>
											 
						 			</div>
									
									
						
						 
				</div><!-- end of food_detail -->
						<div id="ingredient_detail">
								 			<div id="ing_image">
						 					 					<img src="" alt="" class="ing_img" />	
						 					 </div><!-- end of product_images -->
											
											<div id="ing_desc">
													
											</div>
						</div>
						<div class="food_section">
								 <div id="food_header">Food: Select an item to see how to cook it</div>
							  <div class="product_row">
								
							 <#list HooniItems as f>
							
							 			<dl>
												<dt><a href="/foods?fid=${f.id?c}"><img src="/foods/${f.id?c}_thumb.jpg" alt=""></a></dt>
												<dd>${f.title}</dd>
												
										</dl>
							
							 </#list>
							 
							 
							  </div>
						</div>		
						
						
						
						
							
							
        				
        			<div id="ingredients_header">Ingredients: Select an item to see recipe ideas</div>		
							<div id="tabs">
                          <ul>
                              <li><a href="#vegetables"><span>Vegetables</span></a></li>
                              <li><a href="#meats"><span>Meats</span></a></li>
                              <li><a href="#fruits"><span>Fruits</span></a></li>
															<li><a href="#snacks"><span>Snacks</span></a></li>
                          </ul>
                          <div id="vegetables">
                             <div class="product_row">			
													
                             
        							 			
                  							 		<#list vblogs as blog>
                  											
                  											<#assign index = blog.content?index_of(':')>
                  											
                  							 			<#if ( index == -1)><#assign index = blog.content?length> </#if>
                  							 			<dl class="solid">
                  												<dt><a href="javascript:void(0)" class="ing_search" blog_id="${blog.id?c}" name="${blog.content?substring(0,index)}" desc="${blog.content}"><img src="/shares/20_${blog.id?c}_0_thumb.jpg" alt="${blog.content?substring(0,index)}"></a></dt>
                  												<dd>${blog.content?substring(0,index)}</dd>
                  												
                  										</dl>
                  							 
                  							 			</#list>
																</div>
        							 	  </div><!-- end of vegetables -->
								<div id="meats">
                   <div class="product_row">			
													
                             
        							 			
                  							 		<#list mblogs as blog>
                  											
                  											<#assign index = blog.content?index_of(':')>
                  											
                  							 			<#if ( index == -1)><#assign index = blog.content?length> </#if>
                  							 			<dl class="solid">
                  												<dt><a href="javascript:void(0)" class="ing_search" blog_id="${blog.id?c}" name="${blog.content?substring(0,index)}" desc="${blog.content}"><img src="/shares/21_${blog.id?c}_0_thumb.jpg" alt="${blog.content?substring(0,index)}"></a></dt>
                  												<dd>${blog.content?substring(0,index)}</dd>
                  												
                  										</dl>
                  							 
                  							 			</#list>
										</div>
                </div>
                <div id="fruits">
                    
                </div>
								<div id="snacks">
                    
                </div>
					</div><!-- end of tabs -->					
																
																
																
																
							
              
                       
                
							 
							
					
			 		</div><!-- end of contents_left -->
		
				<div id="contents_right">
						  <@JoinForFree />
							
	<div id="today_meals">
			 <div class="today_meals_plan">Your meal plan for today</div>
			 <div class="meal_type breakfast">
			 			<div class="today_header">Breakfast</div>
			 			
				</div>
			 <div class="meal_type lunch">
			 			<div class="today_header">Lunch</div>
			 			
			 </div>
			 <div class="meal_type dinner">
			 			<div class="today_header">Dinner</div>
			 			
			 </div>
			
	</div><!-- end of today_meals -->
	
	<div id="myfavorite">
			 		<div class="favor_header">Your favorite recipes</div>
					<#if ff??>
					<#list ff as f>
								 <div class="favor_item">
					 			<div class="user"><a href="/foods?fid=${f.id?c}">${f.user.userName?string}</a></div>
					 			<div class="thumb"><a href="/foods?fid=${f.id?c}"><img src="/foods/${f.id?c}_thumb.jpg" alt="${f.title?string}" /></a></div>
								<div class="desc"><span class="title"><a href="/foods?fid=${f.id?c}">${f.title?string}</a></span></div>
					 			
								</div>
					</#list>
	 		 		</#if>
					
	</div>
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
<div id="popup"><img src="/images/key.png" alt="" id="popup_img"/></div>
 <@footer />

</body>
</html>
