<#include "include/macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hooni's Food</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/css/styles.css" />
<!--[if IE]><style>@import url("/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/foods.js"></script>
</head>
<body>
<div id="container">

<@mainMenu/>



<div id="contents">
		 <div id="contents_left">

		 <div class="page_title">Foods</div>

		 <div class="form_div">

		 <form name="food_form" action="addrecipe" method="post" id="food_form" enctype="multipart/form-data">
		 <div class="food_comp"><div class="label">Title:</div><input name="title" maxlength="50" size="50"/></div>
		 <div class="food_comp"><div class="label">Snap Shot:</div><input type="file" size="20" name="snap_shot" id="snap_shot" /></div>
     <div class="food_comp"><div class="label">Description:</div><textarea cols="85" rows="6" name="description"></textarea></div>
		 <div class="food_comp"><div class="label">Good for:</div>
		 			<#list mealType as meal>
								 <input type="checkbox" name="${meal?lower_case}" value="${meal.ordinal()}" />${meal?lower_case?cap_first}&nbsp;&nbsp;
					</#list>
		 			
		 </div>
		 <div class="food_comp"><div class="label">Type:</div>
		 			<#list foodType as ft>
								 <input type="radio" name="foodType" value="${ft.ordinal()}" />${ft?lower_case?cap_first}&nbsp;&nbsp;
					</#list>
		 			
		 </div>
		 <div class="food_comp"><div class="label">Keywords:</div><input type="text" name="word0" value="" size="15"/><input type="text" name="word1" value="" size="15"/><input type="text" name="word2" value="" size="15"/><input type="text" name="word3" value="" size="15"/><input type="text" name="word4" value="" size="15"/><input type="text" name="word5" value="" size="15"/></div>
		 <div id="prepare_steps">
				 <div id="steps_title">Steps</div>
				 <div class="one_step">
				 			<div class="food_step_desc"><div class="label">Step 1:</div><textarea name="step1" cols="55" rows="3"></textarea></div>
				 			<div class="food_step_snap"><div class="label">Snap for Step 1:</div><input type="file" name="snap_step1" size="20" id="snap_step1"/></div>
							<div class="food_add_step"><input type="button" value="+ Add Step" class="button" name="addStep" id="addStep"/></div>
				 </div>
				 
		</div><!-- end of prepare_steps -->
							 
		
		<div id="food_submit"><input type="submit" value="Submit" class="button" /><input type="button" value="Cancel" class="button" /></div>

		</form>
		</div><!-- end of form_div -->

				 <@footer />
				 
		</div><!-- end of contents_left -->
		
		<@contentsRight/>
		
</div><!-- end of contents -->


</div><!-- end of container -->

</body>
</html>
