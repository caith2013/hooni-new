
$(function() {
	
		$(".product_item input.delete_button").click(function() {removeFromShoppingCart(this);});
    		
		$("#payment_com #total_amount .total_item #where input.update_button").click(function() {updateShippings();});
		
		$("#payment_com #total_amount .total_item #shippings input.ship_way").click(function() {updateShippingCost(this);});
		
		setupLinksToHttp();
		
		getEstimateShippings();			
	});

function setupLinksToHttp()
{
 		$("#container a.product").each(
									function(index)
									{
									 	var url = "http://www.hooni.org/" + $(this).attr('href');
									 	$(this).attr('href',url);
									
									});

}
function getEstimateShippings()
{
 $.ajax({
 				 url: "https://www.hooni.org/shippingcostajax",
				 cache: false,
				 data: {},
				 success: function(data){$("#shippings").html(data).slideDown; }
 
 });
}
function removeFromShoppingCart(o)
{
 var url = "https://www.hooni.org/shoppingcart?" + "action=delete&pid=" + $(o).attr("pid") + "&date=" + (new Date()).getMilliseconds();
 $(location).attr('href',url);

}

function updateShippings()
{

 var zipcode = $("#where input.zipcode").val();

 $.ajax({
 				 url: "https://www.hooni.org/shippingcostajax",
				 cache: false,
				 data: {"zip" : zipcode},
				 success: function(data){$("#shippings").html(data).slideDown; }
 
 });
}

function updateShippingCost(o)
{
 	var select_cost = parseFloat($(o).val());
	
	$("#payment_com #total_amount .shipping_cost").text('$'+select_cost.toFixed(2));
	$("#payment_com #total_amount #PAYMENTREQUEST_0_SHIPPINGAMT").val(select_cost.toFixed(2));
	
	var total = parseFloat($("#payment_com #total_amount #PAYMENTREQUEST_0_ITEMAMT").val());
		
	total = total + select_cost;
	
	$("#payment_com #total_amount #PAYMENTREQUEST_0_AMT").val(total.toFixed(2));
	$("#payment_com #total_amount #total").text('$'+total.toFixed(2));
	
}