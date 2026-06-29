<ul class="old_shipping">
<#list shipping as s>
<li><a href="javascript:void(0);" class="old_s_item">${s}</a></li>
</#list>
</ul>

<script>
$("a.old_s_item").bind("click", 
						function(){ 
										setShipping($(this).text());
								 }
);
</script>