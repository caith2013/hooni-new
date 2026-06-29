<#include "macros.macro" />
<#include "food.macro" />


<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Foods: Sharing your tastes</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<link href="/hooni/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/hooni/js/food_search.js"></script>

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="A list of dishes from hooni.org" />
<meta name="keywords" content="hooni, foods,food,dish,cooking,vegetables,meats,ingredients" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="Hooni: Sharing your tastes" />
<meta name="product-path" content="/www?op=foods" />
	<meta property="og:type" content="cooking foods"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	
	<meta property="og:description" content="A list of dishes from hooni.org"/>
	<meta property="og:title" content="Sharing your tastes"/>
	<meta property="og:url" content="http://www.hooni.org:80/www?op=foods"/>
	
	
</head>
<body>
<div id="container">

		 <@mainMenu/>
		 <@HomeLeftMenu/>
		 <@FoodLeftMenu/>
		 
		 <div id="contents">
		 			<div id="contents_left">


		 				<div class="page_title">Foods: Sharing your tastes</div>
						
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
																
																
																
																
							
              
                       
                
							 
							 <@footer />
					
			 		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
