<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/aes_no_decrypt.js"></script>
<script type="text/javascript" src="/hooni/js/forgot.js"></script>
</head>
<body>
<div id="container">

<@mainMenu/>

<div class="page_title">
Forgot Password
		
		<#if errorMessage??>
		<div class="error">
		    ${errorMessage}
		</div>
		</#if>
</div>

<div class="form_div">
		 
		 <form name="forgot_form" action="#" method="post" id="forgot_form">
		
		 			 <div class="login_item"><div class="label">Email:</div><input name="email" type="text" value="" class="input_field"/></div>
		 			 <div class="login_item"><div class="label">Security Codes:</div><img src="http://www.hooni.org/key" alt="" id="sc_key" /></div>
					<div class="login_item"><div class="label">Verify Security Codes:</div><input name="vsc" id="vsc" type="text" class="input_field" value=""/></div>
		 			 <div class="login_item"><div class="submit_button"><input type="submit" value="Submit" class="button"/></div></div>
		 			 
		 </form>
		 <form name="forgot_dummy" action="forgotpassword" method="post" id="forgot_dummy">
		 			 
		</form>
</div>

<div id="reg_joinforfree">
   <div id="joinforfree">
			 <input type="button" name="join" value="join for FREE" id="join_button"/>
			 <ul>
			 		 <li>Post your recipes online</li>
					 <li>Plan your recipes for breakfast, lunch or dinner</li>
					 <li>Share your recipes with your friends</li>
					 <li>Post your comments and reviews</li>
					 <li>Save your favorite recipes</li>
					 <li>Make friends and get tips from them</li>
					 <li>Create and post news online</li>
					 <li>Put your ads online for free</li>
					 <li>Create your own blogs</li>
			 </ul>
			 
	  </div>
</div>

</div><!-- end of container -->
<@footer />
</body>
</html>
