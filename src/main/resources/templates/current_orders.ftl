<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hooni: Latest ten orders</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
</head>
<body>
<div id="container">
 
		 <@mainMenu/>
		
		 
		 <div id="contents">
		 			<div id="contents_left">


		 					 <div class="page_title">Latest ten orders: only user "tom" can see this page</div>
							 
							 <div class="orders_container">
							 	<#list orders as order>
							 		
								 	<#assign orderItems = order.orderItems>
								 	<div class="order_item <#if order_index % 2 == 0>even_color<#else>odd_color</#if>">
								 		<div class="order_row">
								 			<div class="row_label">Order Date:</div>
								 			<div class="row_infor">${order.timeCreated}</div>
								 		</div>
								 		<div class="order_row">
								 			<div class="row_label">Paypal User:</div>
								 			<div class="row_infor">${order.client}</div>
								 		</div>
								 		<div class="order_row">
								 			<div class="row_label">Shipping Address:</div>
								 			<div class="row_infor">${order.shippingAddress.name}, ${order.shippingAddress.street1}, ${order.shippingAddress.city}, <#if order.shippingAddress.state??>${order.shippingAddress.state}</#if>, ${order.shippingAddress.zip}, ${order.shippingAddress.countryCode}</div>
								 		</div>
								 		<div class="order_row">
								 			<div class="row_label">Shipping Status:</div>
								 			<div class="row_infor shipping_status">${order.shippingStatus}</div>
								 		</div>
								 		<div class="items_container">
								 		<#list orderItems as item>
								 			<div class="product_item">
								 			<div class="item_row">
								 				<div class="row_label">Product's UPC:</div>
								 				<div class="row_infor">${item.product.upc?string}</div>
								 				
								 			</div>
								 			
								 			<div class="item_row">
								 				<div class="row_label">Product's Title:</div>
								 				<div class="row_infor">${item.product.title?string}</div>
								 			</div>
								 			
								 			<div class="item_row">
								 				<div class="row_label">Product's Brand:</div>
								 				<div class="row_infor">${item.product.brand?string}</div>
								 			</div>
								 			
								 			<div class="item_row">
								 				<div class="row_label">Product's price:</div>
								 				<div class="row_infor">${item.product.price?string.currency}</div>
								 			</div>
								 			</div><!-- end of product_item -->
								 		</#list>
								 		</div><!--end of items_container -->
								 		
								 	</div><!-- end of order_item -->
							 	</#list>
							 	
							 
							 </div><!-- end of order_container -->
										
						
							 
							
							 
			 		</div><!-- end of contents_left -->
		 			
				
					<@footer />
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
