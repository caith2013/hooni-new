$(function() {
						 $.get("http://food.hooni.org/mealplanajax",{date : (new Date()).getMilliseconds()}, function(data){	$("#today_meals").html(data); } );
});