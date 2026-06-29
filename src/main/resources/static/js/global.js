$(function() {
						 
				//$(document).ready(function() { setupFooter(); });
				//$(window).resize(function(){setupFooter();});
				//$(window).load(function() {setupFooter();});
    				
				
	});



function setupFooter()
{
 		$("#container").height(Math.max($(window).height(),$(document).height()));
		$("#footer").css("bottom",'0px');
		
		
}
/*
function findHomepageHeight()
{
 var window_height = $(window).height();
 var news_height = $("#contents_left #sub_container #sub_news").height();
 var products_height = $("#contents_left #sub_container #sub_products").height();
 var foods_height = $("#contents_left #sub_container #sub_foods").height();
 var ads_height = $("#contents_left #sub_container #sub_ads").height();
 var shares_height = $("#contents_left #sub_container #sub_shares").height();
 
 var hv = [window_height, news_height, products_height, foods_height, ads_height, shares_height];
 
 var mh = window_height;
 for (i=0; i < hv.length;  i++)
 {
 	alert(hv[i]);
	if (mh < hv[i]) mh = hv[i];
 }
 
 return mh+120;
}*/