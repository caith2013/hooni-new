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
					
					<div class="page_title">Shopping Cart: <span class="sub_total_color">${total}</span></div>
				
				<div id="products_com">
						 
						 <div class="product_item product_header">
						 			
									<div class="product_title_header">Here is your final order</div>
									<div class="product_price">Price</div>
									<div class="quantity">Quantity</div>
									<div class="cost">Cost</div>
									<div class="product_delete"></div>
						 </div>
						 
						 
						 <div id="total_amount" class="t_font">
						 			<div class="total_item">
											 <div class="label">Total:</div>
						 					 <div class="price">${total}</div>
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
						 					 <div class="price sub_total_color" id="total">${total}</div>
											
									</div>
						 </div>
						
						 
						
				</div><!-- end of products_com -->
		 					 
				<div id="checkout_com">
						 <div id="promot_item"><img src="/products/ads/iphone.jpg" alt="" /></div>
						 
						 <div id="checkout_method">
						 			<div class="method">
											 
											 			 <!-- <input type='image' name='submit' src='https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif' border='0' align='top' alt='Check out with PayPal'/>-->
														 <input type="button" name="submit" value="Submit Payment"/>
											 
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
