<#if food??>
<div class="favor_item">
					 			<div class="user"><a href="/food?fid=${food.id?c}">${food.user.userName?string}</a></div>
					 			<div class="thumb"><img src="/foods/${food.id?c}_thumb.jpg" alt="${food.title?string}" /></div>
								<div class="desc"><span class="title">${food.title?string}</span></div>
					 			
</div>
</#if>
<#if err_msg??>
${err_msg?string}
</#if>


