$(function() {
	
    		$("#my_warranty a.my_warranty_link").click(function() { getLastTenWarranty(); });
				$("#my_warranty span.close").click(function() { $("#old_warranties").html(""); });
				
				
				$("#my_shipping a.my_shipping_link").click(function() { getLastTenShipping(); });
    		$("#my_shipping span.close").click(function() { $("#old_shippings").html(""); });
				
				$("#add_to_cart input.add_cart_button").click(function() { addToShoppingCart(this);});
				
				$("#product_input_form .item_section .label .new_agent_button").click(function() {show_new_agent(this);});
				
				$.get("http://www.hooni.org/shoppingcartajax",{date : (new Date()).getMilliseconds()}, function(data){	$("#shopping_cart").html(data); } );
			
	});
	
	
	
function show_new_agent(o)
{
 statue = $(o).attr("alt");
 if (statue == "new")
 {
 $("#product_input_form .item_section #agent_com").html($("#dummy_agent_com #old_agent").clone());
 	$(o).attr('alt', 'old');
 }
 else
 {

$("#product_input_form .item_section #agent_com").html($("#dummy_agent_com #new_agent").clone());
$(o).attr('alt', 'new');
 }
 
}
function addToShoppingCart(o)
{

 s_pid = $(o).attr("pid")+"";
 s_title = $(o).attr("title")+"";
 s_price = $(o).attr("price")+"";
 s_date = (new Date()).getMilliseconds()+"";
 
 $.ajax({
 				 url: "http://www.hooni.org/shoppingcartajax",
				 cache: false,
				 data: {"pid" : s_pid, "title" : s_title, "price" : s_price, "date" : s_date},
				 success: function(data){ $("#shopping_cart").html(data).slideDown;}
 
 });
 /*
 $.get("http://www.hooni.org/shoppingcartajax", {pid : s_pid, title : s_title, price : s_price, date : s_date},
   function(data)
	 {
	 alert(data);
	 	 	$("#shopping_cart").html(data);
	 }
 );
 */
}

function getLastTenWarranty()
{
	$.get("http://www.hooni.org/warrantyajax", 
		function(data)
		{
		 
			$("#old_warranties").html(data);
			
		}
	);
	
		
 }
 
 function getLastTenShipping()
{
	$.get("http://www.hooni.org/shippingajax", 
		function(data)
		{
			$("#old_shippings").html(data);
			
		}
	);
	
		
}
 
function setWarranty(text)
{	
	
	$("textarea.warranty").val(text);
	$("#old_warranties").html("");
	
}


function setShipping(text)
{
 		$("textarea.shipping").val(text);
		$("#old_shippings").html("");
}