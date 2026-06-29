<#include "macros.macro" />
<#include "policy.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Shopping Cart</title>
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
		 <@leftMenu/>
		 
		 <div id="contents">
		 			<div id="contents_left">
					<form action='paypal' method="post">
					
					<div class="page_title">Shopping Cart: <span class="sub_total_color">${subtotal?string.currency}</span></div>
				
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
									<div class="product_img"><a href="product?pid=${sp.id?c}"><img src="/products/${sp.id?c}_thumb.jpg"></a></div>
									<div class="product_title t_font"><a href="product?pid=${sp.id?c}">${sp.title}</a></div>
									<input type="hidden" value="${sp.title}" name="L_PAYMENTREQUEST_0_NAME${sp_index}" />
									<input type="hidden" value="${sp.id?c}" name="L_PAYMENTREQUEST_0_NUMBER${sp_index}" />
									<input type="hidden" value="" name="L_PAYMENTREQUEST_0_DESC${sp_index}" />
									<div class="product_price number">${sp.price?string.currency}</div>
									<input type="hidden" value="${sp.price?string('##0.00')}" name="L_PAYMENTREQUEST_0_AMT${sp_index}" />
									<div class="quantity number">${sp.quantity?c}</div>
									<input type="hidden" value="${sp.quantity?c}" name="L_PAYMENTREQUEST_0_QTY${sp_index}" />
									<div class="cost number">${(sp.price * sp.quantity)?string.currency}</div>
									
						 </div>
						 </#list>
						 
						 <div id="total_amount" class="t_font">
						 			<div class="total_item">
											 <div class="label">Subtotal:</div>
						 					 <div class="price">${subtotal?string.currency}</div>
									</div>
									<div class="total_item">
											<div class="label">Shipping: </div>
      						 			<div class="price">
          						 			<select>
          						 			<option value="0">US Post ($0.00)</option>
      											<option value="1">UPS (+$7.85)</option>
      											<option value="2">Fedex (+$8.89)</option>
          						 			</select>
          						 	</div>
									</div>
									
									<div class="total_item total_price">
											 <div class="label">Total:</div>
						 					 <div class="price sub_total_color" id="total">${subtotal?string.currency}</div>
											 <input type="hidden" value="${subtotal?string('##0.00')}" name="PAYMENTREQUEST_0_ITEMAMT" />
											 <input type="hidden" value="${subtotal?string('##0.00')}" name="PAYMENTREQUEST_0_AMT" />
									</div>
						 </div>
						
						 
						
				</div><!-- end of products_com -->
		 					 
				<div id="checkout_com">
						 <div id="promot_item"><img src="/products/ads/iphone.jpg" alt="" /></div>
						 
						 <div id="checkout_method">
						 			<div class="method">
											 
											 			  <input type='image' name='submit' src='https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif' border='0' align='top' alt='Check out with PayPal'/>
											 
									</div>
									
						 			<div class="method">
											 method 2
									</div>
									
						 			<div id="policy">
											 <textarea name="policy" cols="65" rows="6">
											 			<@policy />		 
											 </textarea>
											 <div id="policy_check"><input type="checkbox" name="policy_check" checked="checked"/>I agree to the Hooni.org Terms and Policy.</div>
						 			</div><!-- end of policy -->
						 </div><!-- end of checkout_method -->
						 
				</div><!-- end of checkout_com" -->
							
				</form>										 
				
		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->


</div><!-- end of container -->

<@footer />

</body>
</html>
