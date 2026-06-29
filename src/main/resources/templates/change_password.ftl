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
<script type="text/javascript" src="/hooni/js/changepw.js"></script>
</head>
<body>
<div id="container">

<@mainMenu/>

<div class="page_title">
Change Password
		
		<#if errorMessage??>
		<div class="error">
		    ${errorMessage}
		</div>
		</#if>
</div>

<div class="form_div">
		 
		 <form name="change_pw_form" action="#" method="post" id="change_pw_form">
		 			 
		 			 <div class="login_item"><div class="label">Password</div><input name="passwd" type="password" class="input_field" value=""/></div>
					 <div class="login_item"><div class="label">Verify Password</div><input name="vpasswd" type="password" class="input_field" value=""/></div>
					 <div class="login_item"><div class="label">Security Codes:</div><img src="http://www.hooni.org/key" alt="" id="sc_key" /></div>
					<div class="login_item"><div class="label">Verify Security Codes:</div><input name="vsc" id="vsc" type="text" class="input_field" value=""/></div>
					<div class="login_item"><div class="submit_button"><input type="submit" value="Submit" class="button"/></div></div>
		 			
		 </form>
		
		<form name="dummy_change_pw_form" action="changepassword" method="post" id="dummy_change_pw_form">
		 			 
		 			 
		 			
		 </form>
</div>



</div><!-- end of container -->
<@footer />
</body>
</html>
