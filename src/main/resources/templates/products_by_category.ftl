<#include "/include/macros.macro" />
<#include "/include/product.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hooni: Products make life easier</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/css/styles.css" />
<!--[if IE]><style>@import url("/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/product.js"></script>
<script type="text/javascript" src="/js/product_search.js"></script>

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="A list of products improve productivity" />
<meta name="keywords" content="hooni, products,teches,<#list categories as cat>${cat.name?lower_case}<#if cat_has_next>,</#if></#list>" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="Hooni: products make life easier" />
<meta name="product-path" content="/www?op=products" />
	<meta property="og:type" content="products:electronics"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	
	<meta property="og:description" content="Products make life easier"/>
	<meta property="og:title" content="Products make life easier"/>
	<meta property="og:url" content="http://www.hooni.org:80/"/>
	
</head>
<body>
<div id="container">

		 <@mainMenu/>
		  <@HomeLeftMenu/>
			
			
	 <div id="contents">
		      <@ProductLeftMenu/>
		 
		 			<div id="contents_left_product">
					
					 <form name="search_product_middle" id="search_product_middle" metho="post" action="#">
							 		 			 <div id="product_middle_search">
    		 					
    		 
    		 					 			 <div class="product_search_header">Searching/Narrow Your Result:</div>
    		 					 
    		 			
    		 					 			 <input type="text" name="keywords" value="" size="50" class="search_keywords"/>
    							 			 <input type="submit" value="Search" class="search_button mid_button"/><input type="reset" value="Start all over" class="reset_button_top mid_button"/>
					
					
		 					 					 </div>
						</form>
					
							 <div id="product_container">
    							 <div id="product_position"></div>
    							 <div class="sell_product_row search_result">
    							 </div>
    
    		 					 <div class="page_title product_title">Products</div>
    							 <div class="sell_product_row">
    							 <#list HooniItems as p>
    							 
    							 			<dl>
														<dt><a href="/product?pid=${p.id?c}"><img src="/products/${p.id?c}_thumb.jpg" alt=""></a></dt>
    												<dd><a href="/product?pid=${p.id?c}"><#if (p.title?length < 20)>${p.title}<#else>${p.title?substring(0, 20)}</#if></a></dd>
    												<dd><span class="cross_price">${p.price?string.currency}</span>&nbsp;&nbsp;<span class="price_color">${p.finalPrice?string.currency}</span></dd>
    										</dl>
    							 
    							 </#list>
    							 </div>
							 </div><!-- end product_container -->
							 <@footer />
			 		</div><!-- end of contents_left -->
					
					
					
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
