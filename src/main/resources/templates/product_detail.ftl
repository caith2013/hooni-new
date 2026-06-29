<#include "macros.macro" />
<#include "product.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>${product.title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery-1.5.js"></script>
<script type="text/javascript" src="/hooni/js/product.js"></script>
<script type="text/javascript" src="/hooni/js/product_search.js"></script>

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="${product.brand}'s product at www.hooni.org - ${product.description?replace('<li>','')?replace('</li>','')}" />
<meta name="keywords" content="${product.title?replace(' ',',')}" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="${product.title}" />
<meta name="product-path" content="/product?pid=${product.id?c}" />
	<meta property="og:type" content="product"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	<meta property="og:image" content="http://www.hooni.org/products/${product.id?c}_full.jpg"/>
	<meta property="og:description" content="${product.brand}'s product at www.hooni.org - ${product.description?replace('<li>','')?replace('</li>','')}"/>
	<meta property="og:title" content="${product.title}"/>
	<meta property="og:url" content="http://www.hooni.org:80/product?pid=${product.id?c}"/>
</head>
<body>
<div id="container">

		 <@mainMenu/>
		 <@HomeLeftMenu/>
		
		 
		 <div id="contents">
		 			 <@ProductLeftMenu/>
		 			<div id="contents_left_product">

		 					 

					<div id="product_detail">
						
						 			<div class="detail_section">
						 					 <div id="product_title" class="product_title_font">${product.title} <#if owner><span><a href="/product?op=edit&pid=${product.id?c}">Edit</a></span>&nbsp;&nbsp;<span><a href="/product?op=delete&pid=${product.id?c}">Delete</a></span></#if></div>
											  
											 <div id="product_maker">by <a href="javascript:void(0);" class="maker" value="${product.brand?lower_case}">${product.brand}</a></div>
											 
						 			</div>
									
									<div class="detail_section">
						 					 
											<div id="product_infor">
											 		 <div id="product_images">
						 					 					<img src="/products/${product.id?c}_full.jpg" alt="${product.title}" />		
															
						 					 		 </div><!-- end of product_images -->
													
											 		 <div id="price">
              											 <div class="price_kind">
              											 			<div class="label">List Price:</div>
              														<div class="list_price">${(product.price)?string.currency}</div>
              											 </div>
              											 <div class="price_kind">
              											 			<div class="label">Price:</div>
              														<div class="sale_price">${(product.finalPrice)?string.currency}</div>
              											 </div>
              											 <div class="price_kind">
              											 			<div class="label">Your Save:</div>
              														<div class="save_amount">${(product.price - product.finalPrice)?string.currency}</div>
              											 </div>
          												   
          													 <div id="add_to_cart">
																		 	<#if (product.quantity > 0)>
          														 <input type="button" name="addTo" class="add_cart_button button_text" value="Add To Cart" alt="Add to the shopping cart" pid="${product.id?c}" title="${product.title?html}" price="${product.finalPrice}"/>
																			 <#else>
																			 <input type="button" name="addTo" class="add_cart_button button_text" value="Add To Cart" alt="Add to the shopping cart" pid="" title="${product.title?html}" price="${product.finalPrice}"/>
																			 </#if>
          													 </div>
																		 
																		 
						 						   </div><!-- end of price -->
													 
													 <div class="status">
											 							 			<span class="stock_statu"><#if (product.quantity > 0)>In Stock<#else>Out of Stock</#if></span> : <span>${product.shipping}</span>
						 								</div>
						 								<div class="suggestion">
											 							 			Want it delivered faster? Order it before 4pm, and choose One-Day Shipping at checkout.
						 								</div>
														
											</div><!-- end of product_infor -->
											<div id="product_desc">
													
													 <ul>
													 		  ${product.description}
													 </ul>
											</div>
											 
						 			</div>
									
						 			
						 			
						<div id="product_position"></div>
							 <div class="product_row search_result">
							 </div>
						 
				</div><!-- end of product_detail -->
							
														 
														 <div id="product_features">
				
														 </div><!-- end of product_features -->
        
														 <div id="product_facts">
						 
						 								 </div><!-- end of product_facts -->
				
						

							 <@footer />
		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->


</div><!-- end of container -->



</body>
</html>
