<#list HooniItems as f>
							
							 			<dl>
												<dt><a href="/foods?fid=${f.id?c}"><img src="/images/foods/${f.id?c}_thumb.jpg" alt=""></a></dt>
												<dd>${f.title}</dd>
												
										</dl>
							
</#list>