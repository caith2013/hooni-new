<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Knowing the world around you</title>
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


		 					 <div class="page_title">News</div>
							
				<div class="form_div">
        		 <form name="news_input_form" action="news" method="post" id="news_input_form" enctype="multipart/form-data">
        
						<div class="item_section">
        				<div class="label">Title:</div>
        				<div class="data">
        						 <input name="title" type="text" size="80">
        				</div>
        		</div>
						
        		<div class="item_section">
        				<div class="label">Description:</div>
        				<div class="data">
        						<textarea cols="85" rows="10" name="desc"></textarea>
        				</div>
        		</div>
						
						
						<div class="item_section">
        				<div class="label">Picture 1:</div>
        				<div class="data">
        						 <input type="file" size="50" name="news_picture1">
        						 
        				</div>
        		</div>
						
						<div class="item_section">
        				<div class="label">Picture 2:</div>
        				<div class="data">
        						 <input type="file" size="50" name="news_picture2">
        						 
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
		
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->

<@footer />

</body>
</html>
