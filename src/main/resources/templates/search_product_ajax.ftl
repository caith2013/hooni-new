 <form action="productmanager" method="post">
 <#list HooniItems as p>
							 
							 			<dl>
												<dt><a href="/product?pid=${p.id?c}"><img src="/products/${p.id?c}_thumb.jpg" alt=""></a>&nbsp;<#if session.isLoggedIn() && session.user.userName == p.user.userName><span><input type="checkbox" name="products" value="${p.id?c}"/></span></#if></dt>
												<dd><a href="/product?pid=${p.id?c}"><#if (p.title?length < 20)>${p.title}<#else>${p.title?substring(0, 20)}</#if></a></dd>
												<dd><span class="cross_price">${p.price?string.currency}</span>&nbsp;&nbsp;<span class="price_color">${p.finalPrice?string.currency}</span></dd>
										</dl>
							 
</#list>
<#if session.isLoggedIn()>
                    <dl>
                    <dt>
                    <input type="submit" name="delete" value="Delete" class="delete_button"/>
                    </dt>
                    </dl>
</#if>
</form>