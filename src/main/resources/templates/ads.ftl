<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Tell what you need</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
</head>
<body>
<div id="container">

<@mainMenu/>
<div id="contents">
		 			<div id="contents_left">
		 					 <div class="page_title">Advertisements</div>
							
				<div class="form_div">
        		 <form name="ads_input_form" action="ads" method="post" id="ads_input_form" enctype="multipart/form-data">
        
						 <div class="item_section">
        				<div class="label">Subject:</div>
        				<div class="data">
										 <input name="subject" type="text" size="80">
        						 
        				</div>
        		</div>
						<div class="item_section">
        				<div class="label">Keywords:</div>
        				<div class="data">
										 <ul id="keyword">
										 		 <li>
												         <select name="word0">
										 		 				 <option value="" selected="selected"></option>
        						 				 <#list category as cat>
														 				<option value="${cat.name}" <#if cat.name == 'For Sale'>selected="selected"</#if>>${cat.name}</option>
														 </#list>
														 <!--
        						 				 		 <option value="for_sale">For Sale</option>
        						 				     <option value="for_rent">For Rent</option>
        						 				 		 <option value="help_wanted">Help Wanted</option>
																 <option value="people">People</option>
																 <option value="company">Company</option>
																 <option value="jobs">Jobs</option>
																 <option value="house">House</option>
																 <option value="clothes">Clothes</option>
																 <option value="food">Food</option>
																 <option value="travel">Travel</option>
																-->
        						 						 </select>
										 		</li>
										 		 <li><input name="word1" type="text" size="15"></li>
												 <li><input name="word2" type="text" size="15"></li>
												 <li><input name="word3" type="text" size="15"></li>
												 <li><input name="word4" type="text" size="15"></li>
										 </ul>
        						 
        				</div>
        		</div>
						
					
        		<div class="item_section">
        				<div class="label">Description:</div>
        				<div class="data">
        						<textarea cols="85" rows="25" name="desc"></textarea>
        				</div>
        		</div>
						
						<div class="item_section">
        				<div class="label">Contact Information:</div>
        				<div class="data">
        						<textarea cols="85" rows="10" name="contact"></textarea>
        				</div>
        		</div>
						
						
						<div class="item_section">
        				<div class="label">Show your personal contact information:<input type="checkbox" name="personal_infor" checked="checked" value="1"></div>
        				<div class="data">
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
						</div> <!-- end of form_div -->
						
						<@footer />
						
			 		</div><!-- end of contents_left -->
		
<div id="contents_right">
		<div id="shopping_cart">

		</div><!-- end of shopping_cart -->
		
	<div id="my_ad">
					 <div class="ad_item">
					 		<img src="/products/ads/iphone.jpg" alt="" />	
					 </div>
	</div>					
</div><!-- end of contents_right -->

		
		</div><!-- end of contents -->

		
</div><!-- end of container -->

</body>
</html>
