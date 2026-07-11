$(function() {
						 $.get("/mealplanajax",{date : (new Date()).getMilliseconds()}, function(data){	$("#today_meals").html(data); } );
});