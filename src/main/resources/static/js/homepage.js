$(function() {
    	
			//$(document).ready(function() { setupColumn(); });
				//$(window).resize(function(){setupColumn();});
			//	$(window).load(function() {setupColumn();});
		
				$.get("http://www.hooni.org/shoppingcartajax",{date : (new Date()).getMilliseconds()}, function(data){	$("#shopping_cart").html(data); } );
	});


	function setupColumn()
	{
	 var col_height = Math.max($("#contents_left #sub_container #sub_products").height(),$("#contents_left #sub_container #sub_news").height());
	 col_height = Math.max(col_height,$("#contents_left #sub_container #sub_foods").height());
	 col_height = Math.max(col_height,$("#contents_left #sub_container #sub_ads").height());
	 col_height = Math.max(col_height,$("#contents_left #sub_container #sub_shares").height());
	
		$("#contents_left #sub_container .sub_item").each(
													function(index){
													
													$(this).height(col_height);
													
													});
		 
	}