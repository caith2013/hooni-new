<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Share your expertise and blog about it</title>
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
		 					 <div class="page_title">Shares</div>
				
							
				<div class="form_div">
        		 <form name="shares_input_form" action="share" method="post" id="shares_input_form" enctype="multipart/form-data">
        
						 <div class="item_section">
        				<div class="label">Subject:</div>
        				<div class="data">
										 <input name="subject" type="text" size="80">
        						 
        				</div>
        		</div>
						
						
						
        		<div class="item_section">
    						<div class="item_section blog_section">
            				<div class="label blog_title">Offering:</div>
            				<div class="data">
             				</div>
            		</div>
        				<div class="label">Describe your offering:</div>
        				<div class="data">
        						<textarea cols="85" rows="10" name="offering"></textarea>
        				</div>
        				</div>
						<div class="label">Offering rate:</div>
        				<div class="data">
        						<div id="rate">
												 <div id="by">
												 			<input type="radio" name="rateby" value="year" />Yearly
															<input type="radio" name="rateby" value="month" />Monthly
															<input type="radio" name="rateby" value="week" />Weekly
															<input type="radio" name="rateby" value="day" />Daily
															<input type="radio" name="rateby" value="hour" />Hourly
															<input type="radio" name="rateby" value="fixed" />Fixed
												 </div>
												 <div id="priceby">$<input type="text" value="0" name="price" size="15"></div>
									  <div>
        				</div>
        		</div>
						
						<div class="item_section blog_section">
        				<div class="label blog_title">Blog your offering</div>
        				<div class="data">
         				</div>
        		</div>
						
						<div class="item_section">
        				<div class="label">First blog:</div>
        				<div class="data">
        						<textarea cols="50" rows="10" name="blogging"></textarea>
        				</div>
        		</div>
						
						<div class="item_section">
        				<div class="label">Picture 1:</div>
        				<div class="data">
        						 <input type="file" size="50" name="shares_picture1">
        						 
        				</div>
        		</div>
						
						<div class="item_section">
        				<div class="label">Picture 2:</div>
        				<div class="data">
        						 <input type="file" size="50" name="shares_picture2">
        						 
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
<@footer />
</body>
</html>
