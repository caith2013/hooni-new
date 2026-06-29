$(function() {
				setupTabs();
				$("#food_home_header #header #search #search_text").focus(function(){hideText(this);});
				$("#food_home_header #header #search #search_text").blur(function(){showText(this);});
				$("#food_home_header #header #search #search_button").click(function(){headerSearch(this);});
				
				$("#food_left_menu input.meal_type").click(function() {narrowResult(this);});
				$("#food_left_menu input.food_type").click(function() {narrowResult(this);});
				$("#food_left_menu input.search_button").click(function() {narrowResult(this);});
				
				$("#vegetables a.ing_search").click(function() {searchFoodsByIng(this);});
				
				$("#food_left_menu #search_food_form").submit(function(){	 narrowResult(this); return false;});
				
				$("#addto a.addto").click(function(){addToPlanCart(this);});
				
				$("#more_and_favor a.morelike").click(function(){searchMoreLike(this);});
				
				$("#more_and_favor a.favorite").click(function(){addToFavorite(this);});
				
				$("input.join_button").click(function(){$(location).attr('href',"https://www.hooni.org/register");});
				
				$("#food_home_header #search_food_header").submit(function(){	 headerSearch(this); return false;});
				
});


function addToFavorite(o)
{
 var s_fid = $(o).attr("fid");
  
 $.ajax({
						url: "http://food.hooni.org/favoriteajax",
						cache: false,
						data: {"fid" : s_fid},
						traditional: true,
						success: function(data) {$("#myfavorite .favor_header").after(data).slideDown("slow");},
						error: function(data) {$(location).attr('href',"https://www.hooni.org/login");}
					 });
}
function searchMoreLike(o)
{
 	 var a_mealType = [];
   var s_foodType = $(o).attr("foodtype");
   var a_keywords = [];
	 
	 callSearchAjax(a_mealType,s_foodType,a_keywords);
	 
	 
}
function headerSearch(o)
{
 	 $('#food_home_header #header #search #search_text').unbind();
 
 	 var a_mealType = [];
   var s_foodType = "";
   var a_keywords = [];
	 
	  
	 a_keywords = $('#food_home_header #header #search #search_text').val().split(/[\s()]+/);

   callSearchAjax(a_mealType,s_foodType,a_keywords);
	 
	
}
function hideText(o)
{
 $(o).val('');
}

function showText(o)
{
 $(o).val("Example:broccoli");
}

function setupTabs()
{
 				 $(document).ready(function() {
															 $("#tabs").tabs();
															 $("#tabs").css('top', '20px');
															 $("#tabs").css('width', '100%');
															 setTabHeight();
										});
				$(window).resize(function(){setTabHeight();});
				$(window).load(function() {setTabHeight();});
}

function setTabHeight()
{
 			 $("#tabs").css('clear','both');
															
			$("#tabs").height($("#tabs #vegetables dl:last").offset().top - $("#tabs").offset().top + 125);	 
}
function searchFoodsByIng(o)
{
   var a_mealType = [];
   var s_foodType = "";
   var a_keywords = [];
	 
	 var name = $(o).attr("name");
	 var blog_id = $(o).attr("blog_id");
	 var desc = $(o).attr("desc");
	 
	 a_keywords = name.toLowerCase().split(/[\s()]+/);

   callSearchAjax(a_mealType,s_foodType,a_keywords);
	 
	
}

function populateIngDetail(name,blog_id,desc)
{
 		$("#ingredient_detail #ing_image").show();
 		$("#ingredient_detail #ing_image img.ing_img").attr("src","/shares/20_" +blog_id + "_0_full.jpg");
		
		$("#ingredient_detail #ing_desc").html(""+desc);
}
function narrowResult(o)
{
 	 var a_mealType = [];
 	 $("#food_left_menu input.meal_type:checked").each(
									 function(index)
									 {
											a_mealType[index] = $(this).val();
									 }
		);
 		//alert(a_mealType);
		var s_foodType = $("#food_left_menu input.food_type:checked").val();
		
		//alert(s_foodType);
		var a_keywords = [];
		
		var s_keywords = $("#food_left_menu input.search_keywords").val();
		
		//alert(s_keywords);
		
		a_keywords = s_keywords.split(/\s+/);
		
		//alert(a_keywords);
		callSearchAjax(a_mealType,s_foodType,a_keywords);
		/*
		$.ajax({
						url: "http://www.hooni.org/searchfoodajax",
						cache: false,
						data: {"mealType" : a_mealType, "foodType" : s_foodType, "keywords" : a_keywords},
						traditional: true,
						success: function(data) {$("#contents_left .food_section .product_row").html(data).slideDown;}
					 });
		*/
}

function callSearchAjax(a_mealType,s_foodType,a_keywords)
{
 $.ajax({
						url: "http://food.hooni.org/searchfoodajax",
						cache: false,
						data: {"mealType" : a_mealType, "foodType" : s_foodType, "keywords" : a_keywords},
						traditional: true,
						success: function(data) {$("#contents_left .food_section .product_row").html(data).slideDown('slow'); $(window).scrollTop(300);}
					 });
}


function addToPlanCart(o)
{

 s_fid = $(o).attr("fid")+"";
 s_title = $(o).attr("title")+"";
 s_mealtype = $(o).attr("mealtype")+"";
 
 $.ajax({
 				 url: "http://food.hooni.org/addtomealplanajax",
				 cache: false,
				 data: {"fid" : s_fid, "title" : s_title, "mealtype" : s_mealtype},
				 traditional: true,
				 success: function(data){ addToMealPlan(data,s_mealtype)}
 });
}

function addToMealPlan(data,type)
{
 switch(type)
 {
 	case "0":
			 $("#today_meals .breakfast .today_header").after(data);
			 break;
	case "1":
			 $("#today_meals .lunch .today_header").after(data);
			 break;
	case "2": 
			 $("#today_meals .dinner .today_header").after(data);
			 break;
	default:
			 break;
 }
 
}

