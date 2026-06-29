<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Edit product</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/product.js"></script>
</head>
<body>
<div id="container">

		 <@mainMenu/>
	
		 
		 <div id="contents">
		 			<div id="contents_left">

		 					 <div class="page_title">Edit Product: ${product.id?c}</div>
							 
							 <div class="form_div">
        <form name="product_input_form" action="product?pid=${product.id?c}" method="post" id="product_input_form" enctype="multipart/form-data">
        
						<div class="item_section">
        				<div class="label">Title:</div>
        				<div class="data">
        						 <input name="title" type="text" size="80" value="${product.title}">
        				</div>
        		</div>
						
        		<div class="item_section">
        				<div class="label">Product Description:</div>
        				<div class="data">
        						<textarea cols="85" rows="10" name="desc">${product.description}</textarea>
        				</div>
        		</div>
        		
        		<div class="item_section">
        				<div class="label">Bar Code:</div>
        				<div class="data">
        						 <input name="barcode" type="text" size="80" value="${product.upc}">
        						 
        				</div>
        		</div>
						
						<div class="item_section">
        				<div class="label">Properties:</div>
        				<div class="data">
        						 <ul id="properties">
												<li><span>Weight:&nbsp;</span><input name="pounds" value="0" size="5"/>lbs<input name="ounces" value="${product.weight}" size="10">oz</li>
												<li><span>Width:&nbsp;</span><input name="width" value="${product.width}" size="5"/>inches</li>
												<li><span>Height:&nbsp;</span><input name="height" value="${product.height}" size="5"/>inches</li>
												<li><span>Length:&nbsp;</span><input name="length" value="${product.length}" size="5"/>inches</li>
										</ul>
        						 
        				</div>
        		</div>
        		
        		
        		<div class="item_section">
        				<div class="label">Category:</div>
        				<div class="data">
        						 <select name="category">
										
										 <#list categorie_names as cat>
        						 				 <#if cat==product.category>
        						 				 <option value="${cat}" selected="selected">${cat?string}</option>
														 <#else>
														 <option value="${cat}">${cat?string}</option>
														 </#if>
        							</#list>			
        						 </select>
        						 OR <br/>
        						 New Category:
        						 <input name="new_cat" type="text" size="25">
        						 
        				</div>
        		</div>
        		
        		<div class="item_section">
        				<div class="label">Brand:</div>
        				<div class="data">
        						 <select name="brand">
        						
        						 	<#list brand_names as b>
												<#if b==product.brand>
												 <option value="${b}" selected="selected">${b?string}</option>
												 <#else>
												 <option value="${b}">${b?string}</option>
												 </#if>
											</#list>
        						 </select>
        						 OR <br/>
        						 New Brand:
        						 <input name="new_brand" type="text" size="25">
        						 
        				</div>
        		</div>
        		
        		
        		<div class="item_section">
        				<div class="label">Price:</div>
        				<div class="data">
        						 <input name="price" type="text" size="25" value="${product.price?string("0.##")}">
        						 
        				</div>
        		</div>
        	
        	<div class="item_section">
        				<div class="label">Agent: <input type="button" name="new_agent_button" value="New" class="new_agent_button" alt="old"/></div>
        				<div class="data">
										 <div id="agent_com">
										 			<div id="old_agent">
													<#list product_agents as product_agent>
															 <div class="agent_item">
															 			<div class="input_data">
										 									${product_agent.agent.name?string}
																		</div>
																		<div class="input_label">Agent Name</div>
															 </div>
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="agent_cost_${product_agent.agent.id?c}" value="${product_agent.cost?string("0.##")}" size="25"/>
																		</div>
																		<div class="input_label">Cost of the product from this agent</div>
															 </div>
													</#list>
																<div class="agent_item">
															 			<div class="input_data">
										 					 				<select name="old_agent_name">
																			<#list agents as agent>
																			
																			<option value="${agent.id?c}">${agent.name?string}</option>
																			
																			</#list>
																			</select>
																		</div>
																		<div class="input_label">Agent Name</div>
															 </div>
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="cost" value="" size="25"/>
																		</div>
																		<div class="input_label">Cost of the product from this agent</div>
															 </div>
													</div>
										 </div><!-- agent_com end -->
      				  </div>
        		</div>
        	
        		<!--
        		<div class="item_section">
        				<div class="label">Picture:</div>
        				<div class="data">
        						 <input type="file" size="50" name="product_picture">
        						 
        				</div>
        		</div>
        		-->
						<div class="item_section">
        				<div class="label" id="warranty">Warranty:</div>
        				<div class="data">
        						 <textarea cols="85" rows="5" name="warranty" class="warranty">${product.warranty}</textarea>
        						 <div id="my_warranty"><a href="javascript:void(0);" class="my_warranty_link">My Warranty</a><span class="close">&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);">Close</a></span></div>
										 <div id="old_warranties"></div>
        				</div>
        		</div>
						
						<div class="item_section">
        				<div class="label" id="shipping">Shipping:</div>
        				<div class="data">
        						 <textarea cols="85" rows="5" name="shipping" class="shipping">${product.shipping}</textarea>
        						 <div id="my_shipping"><a href="javascript:void(0);" class="my_shipping_link">My Shippings</a><span class="close">&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);">Close</a></span></div>
										 <div id="old_shippings"></div>
        				</div>
        		</div>
						
						
        		<div class="item_section">
        				<div class="label"></div>
        				<div class="data">
        				<br/>
        				<br/>
        						 <input type="submit" name="Submit" value="Submit" />
        						 
        				</div>
        		</div>
        </form>
        </div><!-- end of form_div -->



			 </div><!-- end of contents_left -->
		
			 <@contentsRight/>
		
		</div><!-- end of contents -->

		

</div><!-- end of container -->

<@footer />
				 	 		 		<div id="dummy_agent_com">
				 	 		 							<div id="old_agent">
															 <#list product_agents as product_agent>
															 <div class="agent_item">
															 			<div class="input_data">
										 									${product_agent.agent.name?string}
																		</div>
																		<div class="input_label">Agent Name</div>
															 </div>
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="agent::${product_agent.agent.id}::cost" value="${product_agent.cost?string("0.##")}" size="25"/>
																		</div>
																		<div class="input_label">Cost of the product from this agent</div>
															 </div>
													     </#list>
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<select name="old_agent_name">
																			<#list agents as agent>
																			
																			<option value="${agent.id?c}">${agent.name?string}</option>
																			
																			</#list>
																			</select>
																		</div>
																		<div class="input_label">Agent Name</div>
															 </div>
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="cost" value="" size="25"/>
																		</div>
																		<div class="input_label">Cost of the product from this agent</div>
															 </div>
													</div>
													<div id="new_agent">
															 
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="new_agent_name" value="" size="25"/>
																		</div>
																		<div class="input_label">Agent Name</div>
															 </div>
															 
															 <div class="agent_item">
															 			<div class="input_data">
										 					 					 <input type="text" name="agent_address" value="" size="50"/>
																		</div>
																		<div class="input_label">Address</div>
															 </div>
															 
															 
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="agent_city" value="" size="25"/>
																		</div>
																		<div class="input_label">Agent City</div>
															 </div>
															 
															 
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="agent_state" value="" size="25"/>
																		</div>
																		<div class="input_label">Agent State</div>
															 </div>
															 
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="agent_zip" value="" size="25"/>
																		</div>
																		<div class="input_label">Agent Zip</div>
															 </div>
															 
															 <div class="agent_item">
															 			<div class="input_data">
																				 <select id="agent_country" name="agent_country">
																				 				 <option value="" selected="selected"></option>
																								 <option value=""></option>
																				 </select>
										 					 				
																		</div>
																		<div class="input_label">Agent Country</div>
															 </div>
															 
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="agent_phone" value="" size="25"/>
																		</div>
																		<div class="input_label">Agent Phone</div>
															 </div>
															 
															 
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="agent_manager" value="" size="25"/>
																		</div>
																		<div class="input_label">Agent Manager</div>
															 </div>
															 
															 
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="agent_email" value="" size="25"/>
																		</div>
																		<div class="input_label">Agent Email</div>
															 </div>
															 
															 <div class="agent_item">
															 			<div class="input_data">
										 					 				<input type="text" name="cost" value="" size="25"/>
																		</div>
																		<div class="input_label">Cost of the product from this agent</div>
															 </div>
															 
													</div><!-- new agent end here -->
										</div>
</body>
</html>
