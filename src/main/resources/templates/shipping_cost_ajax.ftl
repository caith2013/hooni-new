<#if !free>
		 <#assign x = 8.00>
		 <div class="ship"><input name="shipping" type="radio" value="${x?string('##0.00')}" class="ship_way"/>Shipping Cost ${x?string.currency}</div>
<#else>
    <div class="ship"><input name="shipping" type="radio" value="0" class="ship_way"/>Free Shipping 7-10 days</div>
    <#if first??><div class="ship"><input name="shipping" type="radio" value="${first?string('##0.00')}" class="ship_way"/>Regular 5-7 days(${first?string.currency})</div></#if>
    <#if priority??><div class="ship"><input name="shipping" type="radio" value="${priority?string('##0.00')}" class="ship_way"/>Fast 3-5 days(${priority?string.currency})</div></#if>
    <#if express??><div class="ship"><input name="shipping" type="radio" value="${express?string('##0.00')}" class="ship_way"/>Express 1-2 days(${express?string.currency})</div></#if>
</#if>
<script>
$("#payment_com #total_amount .total_item #shippings input.ship_way").click(function() {updateShippingCost(this);});

$(".ship input.ship_way").first().attr("checked", "checked");

var select_cost = parseFloat($(".ship input.ship_way").first().val());
	
	$("#payment_com #total_amount .shipping_cost").text('$'+select_cost.toFixed(2));
	$("#payment_com #total_amount #PAYMENTREQUEST_0_SHIPPINGAMT").val(select_cost.toFixed(2));
	
	var total = parseFloat($("#payment_com #total_amount #PAYMENTREQUEST_0_ITEMAMT").val());
		
	total = total + select_cost;
	
	$("#payment_com #total_amount #PAYMENTREQUEST_0_AMT").val(total.toFixed(2));
	$("#payment_com #total_amount #total").text('$'+total.toFixed(2));
</script>