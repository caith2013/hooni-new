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
		
		 
		 <div id="contents">
		 			<div id="contents_left">
					<form action='https://www.hooni.org/paypal' method="post" class="checkout">
					
					<div class="page_title">Shopping Cart: <span class="sub_total_color">${subtotal?string.currency}</span> <div class="continue"><a href="http://www.hooni.org">Continue Shopping</a></div></div>
				
				<div id="products_com">
						 
						 <div class="product_item product_header">
						 			
									<div class="product_title_header">Your items in the shopping cart</div>
									<div class="product_price">Price</div>
									<div class="quantity">Quantity</div>
									<div class="cost">Cost</div>
									<div class="product_delete"></div>
						 </div>
						 <#list s_products as sp>
						 <div class="product_item <#if sp_index % 2 == 0>even_color<#else>odd_color</#if>">
									<div class="product_img"><a href="product?pid=${sp.id?c}" class="product"><img src="/products/${sp.id?c}_thumb.jpg"></a></div>
									<div class="product_title t_font"><a href="product?pid=${sp.id?c}" class="product">${sp.title}</a></div>
									<input type="hidden" value="${sp.title}" name="L_PAYMENTREQUEST_0_NAME${sp_index}" />
									<input type="hidden" value="${sp.id?c}" name="L_PAYMENTREQUEST_0_NUMBER${sp_index}" />
									<input type="hidden" value="" name="L_PAYMENTREQUEST_0_DESC${sp_index}" />
									<div class="product_price number">${sp.price?string.currency}</div>
									<input type="hidden" value="${sp.price?string('##0.00')}" name="L_PAYMENTREQUEST_0_AMT${sp_index}" />
									<div class="quantity number">${sp.quantity?c}</div>
									<input type="hidden" value="${sp.quantity?c}" name="L_PAYMENTREQUEST_0_QTY${sp_index}" />
									<div class="cost number">${(sp.price * sp.quantity)?string.currency}</div>
									
									
									<div class="product_delete"><input type="button" name="delete" value="Delete" pid="${sp.id?c}" class="delete_button"></div>
						 </div>
						 </#list>
						</div><!-- end of products_com -->
						
						<div id="payment_com">
						 <div id="total_amount" class="p_font">
						 			<div class="total_item">
											 <div class="label">Subtotal:</div>
						 					 <div class="price sub_total_color">${subtotal?string.currency}</div>
											
									</div>
									<div class="total_item shipping_com_height">
									  <div class="shipping_com">
												 <div class="s_header">
												 			<div class="label">Shipping: </div>
												 			<div class="price sub_total_color shipping_cost">$0.00</div>
														  <input type="hidden" value="0.00" name="PAYMENTREQUEST_0_SHIPPINGAMT" id="PAYMENTREQUEST_0_SHIPPINGAMT"/>
												 </div>
												 <div id="where">
														 		<span style="font: normal 11px arial, san-serif;">Ship to where? Enter zipcode:<input type="text" name="zipcode" size="5" style="font: normal 11px arial, san-serif;" class="zipcode"/><input type="button" name="s_up" value="update" id="s_up" style="font: bold 12px arial, san-serif; color: blue;" class="update_button"/></span>
												</div>
												<div id="shippings">
														 		
												</div>
									  </div><!-- shipping_com -->
									</div> <!-- totoal_item -->
									
									<div class="total_item total_price">
											 <div class="label">Total:</div>
						 					 <div class="price sub_total_color" id="total">${subtotal?string.currency}</div>
											 <input type="hidden" value="${subtotal?string('##0.00')}" name="PAYMENTREQUEST_0_ITEMAMT" id="PAYMENTREQUEST_0_ITEMAMT" />
											 <input type="hidden" value="${subtotal?string('##0.00')}" name="PAYMENTREQUEST_0_AMT" id="PAYMENTREQUEST_0_AMT" />
									</div>
								
						 </div> <!-- total_amount -->
						
						 
						
				</div><!-- end of payment_com -->
		 					 
				<div id="checkout_com">
						
						 
						 <div id="checkout_method">
						 			<div class="method">
											 			
														
											 			 <div class="label">You don't need a PayPal account to checkout===></div>
														
											 			<div class="sub_button"><input type='image' name='submit' src='https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif' border='0' align='top' alt='Check out with PayPal'/></div>
														
														
									</div>
									<div class="method">
											 <div class="label">We accept:</div>
											 <div class="sub_button"><img src='images/ccard.gif' alt="We accept major credit cards"/></div>
						 			</div>
									
						 			<div id="policy">
											 
											 <div id="policy_check"><input type="checkbox" name="policy_check" checked="checked"/><a href="http://www.hooni.org/footer?op=policy">I agree to the Hooni.org Terms and Policy.</a></div>
						 			</div><!-- end of policy -->
						 </div><!-- end of checkout_method -->
						 
				</div><!-- end of checkout_com" -->
							
				</form>										 
				
				<@footer />
				
				
		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->


</div><!-- end of container -->



</body>
</html>
