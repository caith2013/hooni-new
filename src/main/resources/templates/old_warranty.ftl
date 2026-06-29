<ul class="old_warranty">
<#list warranty as w>
<li><a href="javascript:void(0);" class="old_w_item">${w}</a></li>
</#list>
</ul>
<script>
$("a.old_w_item").bind("click", 
						function(){ 
										setWarranty($(this).text());
								 }
);
</script>