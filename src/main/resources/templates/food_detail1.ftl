<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Food Detail Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/popup.js"></script>

</head>
<body>
<div id="container">

		 <@mainMenu/>
		
		 
		 <div id="contents">
		 			<div id="contents_left">

		 					 

					<div id="food_detail">
				
						 			<div class="detail_section">
						 					 <div id="food_title" class="food_title_font">${food.title} </div>
											  
											 <div id="food_maker">By <a href="#">${food.user.userName}</a> on ${food.timeCreated?string("EEEE, MMMM dd, yyyy")}</div>
											 
						 			</div>
									
									<div class="detail_section">
						 					 
											 <div id="food_images">
						 					 			<img src="/foods/${food.id?c}_full.jpg" alt="" />			
						 					 </div><!-- end of food_images -->
											
											<div id="food_desc">
													 <div class="food_description">${food.description}</div>
													<ol>
												
															<#list food_steps as foodStep>
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

<div id="popup"><span class="close"></span><img src="" alt="" id="popup_img"/></div>



</body>
</html>
