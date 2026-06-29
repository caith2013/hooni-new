					 <div id="shopping_cart_button">
					 			<input type="button" name="cart" value="Shopping Cart" class="button_text" id="cart_button">
					 			<!--<a href="shoppingcart" class="button_text">Shopping Cart</a>-->
					 </div>
					 <div class="seperate_line"></div>
					 
					 <#list s_products as sp>
					 		<div class="one_item <#if sp_index % 2 == 0>even_color<#else>odd_color</#if>">
					 			<a href="http://www.hooni.org/product?pid=${sp.id?c}"><img src="/products/${sp.id?c}_mini.jpg" alt="" />${sp.title?html?substring(0,15)} : ${sp.price?string.currency}(${sp.quantity?c})==>${(sp.price * sp.quantity)?string.currency}</a>
					 		</div>
					 </#list>
					 
					 <div class="seperate_line"></div>
					 
					 <div id="sub_total_price">
					 			The Subtotal: <span class="sub_total_color">${subtotal?string.currency}</span>
					 </div>
					 
<script>
	$("#shopping_cart_button #cart_button")
	.click(
				function()
				{
				 var url = "https://www.hooni.org/shoppingcart?date=" + (new Date()).getMilliseconds();
				  $(location).attr('href',url);
				} 
	);
</script>					 
