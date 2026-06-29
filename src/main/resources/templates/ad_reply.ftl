<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>${ad.subject}</title>
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


		 					 <div class="page_title">You reply to: ${ad.subject}</div>
							
				<div class="form_div">
        		 <form name="ad_reply_form" action="adsreply" method="post" id="ads_reply_form">
						 <input type="hidden" name="ad_id" value="${ad.id?c}"/>
        		 <input type="hidden" name="reply_email" value="${ad.user.email}"/>
						<div class="item_section">
        				<div class="label">Reply To:</div>
        				<div class="data">
        						 <input name="title" type="text" size="80" value="${ad.user.userName}:${ad.user.email}" readonly="readonly"/>
        				</div>
        		</div>
						<div class="item_section">
        				<p/>
        		</div>
						
        		<div class="item_section">
        				<div class="label">Your message:</div>
        				<div class="data">
        						<textarea cols="85" rows="10" name="reply_message"></textarea>
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
		
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
