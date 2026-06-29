<#include "macros.macro" />
<#include "policy.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Your final order</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/product.js"></script>
<script type="text/javascript" src="/hooni/js/shoppingcart.js"></script>
</head>
<body>
<div id="container">

		 <@mainMenu/>
		 
		 
		 <div id="contents">
		 			<div id="contents_left">
					<form action='paypal' method="post">
					
					<div class="page_title">Confirmation of your order#: ${orderNum?c} </div>
					
					<div id="write_down">Please write down your order number in case you need to contact us for any questions.</div>
					
				<div id="products_com">
						 
						 <div class="product_item product_header">
						 			
									<div class="product_title_header">Here is your final order</div>
									<div class="product_price">Price</div>
									<div class="quantity">Quantity</div>
									<div class="cost">Cost</div>
									<div class="product_delete"></div>
						 </div>
						 
						 <#list s_products as sp>
						 <div class="product_item <#if sp_index % 2 == 0>even_color<#else>odd_color</#if>">
									<div class="product_img"><a href="product?pid=${sp.product.id?c}"><img src="/products/${sp.product.id?c}_thumb.jpg"></a></div>
									<div class="product_title t_font"><a href="product?pid=${sp.product.id?c}">${sp.product.title}</a></div>
									
									<div class="product_price number">${sp.product.finalPrice?string.currency}</div>
									
									<div class="quantity number">${sp.quantity?c}</div>
									
									<div class="cost number">${(sp.product.finalPrice * sp.quantity)?string.currency}</div>
									
								
						 </div>
						 </#list>
						 
				</div><!-- end of products_com -->
						
				<div id="payment_com">
						 
						 <div id="total_amount" class="t_font">
						 			<div class="total_item">
											 <div class="label">SubTotal:</div>
						 					 <div class="price">${(total - shipping_cost)?string.currency}</div>
									</div>
									<div class="total_item">
											<div class="label">Shipping: </div>
      						 			<div class="price">
          						 			${shipping_cost?string.currency}
          						 	</div>
									</div>
									
									<div class="total_item total_price">
											 <div class="label">Total:</div>
						 					 <div class="price sub_total_color" id="total">${total?string.currency}</div>
											
									</div>
						 </div>
						
						 
						
				</div><!-- end of payment_com -->
		 					 
				<div id="checkout_com">
						 
				</div><!-- end of checkout_com" -->
							
				</form>										 
				
		</div><!-- end of contents_left -->
		
					
		
		</div><!-- end of contents -->


</div><!-- end of container -->

<@footer />

</body>
</html>
