       <div class="today_meals_plan">Your meal plan for today</div>
			 <div class="meal_type breakfast">
			 			<div class="today_header">Breakfast</div>
						<#if breakfast??>
								 <#list breakfast as bf>
								 				<div class="today_item">
			 											 <div class="title"><span class="title"><a href="/foods?fid=${bf.id?c}">${bf.title}</a></span></div>
					 									 <div class="thumb"><a href="/foods?fid=${bf.id?c}"><img src="/images/foods/${bf.id?c}_thumb.jpg" alt="${bf.title}"/></a></div>
												</div>		 				
								 </#list>
						</#if>
			 			
				</div>
			 <div class="meal_type lunch">
			 			<div class="today_header">Lunch</div>
			 			<#if lunch??>
								 <#list lunch as lun>
								 				<div class="today_item">
			 											 <div class="title"><span class="title"><a href="/foods?fid=${lun.id?c}">${lun.title}</a></span></div>
					 									 <div class="thumb"><a href="/foods?fid=${lun.id?c}"><img src="/images/foods/${lun.id?c}_thumb.jpg" alt="${lun.title}"/></a></div>
												</div>		 				
								 </#list>
						</#if>
			 </div>
			 <div class="meal_type dinner">
			 			<div class="today_header">Dinner</div>
			 			<#if dinner??>
								 <#list dinner as d>
								 				<div class="today_item">
			 											 <div class="title"><span class="title"><a href="/foods?fid=${d.id?c}">${d.title}</a></span></div>
					 									 <div class="thumb"><a href="/foods?fid=${d.id?c}"><img src="/images/foods/${d.id?c}_thumb.jpg" alt="${d.title}"/></a></div>
												</div>		 				
								 </#list>
						</#if>
			 </div>
			  <div id="start_cooking">
			 			<input type="button" name="start_cooking" class="start_cooking_button" value="Start Cooking"/>
			 </div>
			 <script type="text/javascript">
			 $(function() {
						 						 $("input.start_cooking_button").click(function(){$(location).attr('href',"/startcooking");});
			});
			 
			 </script>