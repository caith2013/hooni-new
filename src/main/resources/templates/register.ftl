<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hooni Register</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/aes_no_decrypt.js"></script>
<script type="text/javascript" src="/hooni/js/register.js"></script>

</head>
<body>

<div id="container">

<@mainMenu/>


<div class="page_title">
Register
<#if errors??>
<div class="error">
    Please correct the following errors before submit:
    <ul>
    <#assign keys = errors?keys>
    <#list keys as k>
    <li>${k}: ${errors[k]}</li>
    </#list>
    </ul>
</div>
</#if>

</div>


<div class="form_div">
		
    <form name="register_form" action="#" method="post" id="register_form">
		
		<div class="register_item"><div class="label">User Name:</div><input name="userName" id="userName" maxlength="25" type="text" value="" class="input_field"/><div class="validate"><div class="error userNameError"></div><img src="/hooni/images/check.jpg" alt="" class="userNameImg"/></div></div>
    <div class="register_item"><div class="label">Email:</div><input name="email" id="email" maxlength="50" type="text" class="input_field" value="" /><div class="validate"><div class="error emailError"></div><img src="/hooni/images/check.jpg" alt="" class="emailImg"/></div></div>
		<div class="register_item"><div class="label">First Name:</div><input name="firstName" id="firstName" maxlength="25" type="text" class="input_field" value="" /><div class="validate"><div class="error firstNameError"></div><img src="/hooni/images/check.jpg" class="firstNameImg" alt="" /></div></div>
    <div class="register_item"><div class="label">Last Name:</div><input name="lastName" id="lastName" maxlength="25" type="text" class="input_field" value=""/><div class="validate"><div class="error lastName"></div><img src="/hooni/images/check.jpg" alt="" class="lastNameImg"/></div></div>
    <div class="register_item"><div class="label">Password:</div><input name="passwd" id="passwd" maxlength="25" type="password" class="input_field" value=""/><div class="validate"><div class="error passwdError"></div><img src="/hooni/images/check.jpg" alt="" class="passwdImg"/></div></div>
    <div class="register_item"><div class="label">Verify Password:</div><input name="vpasswd" id="vpasswd" maxlength="25" type="password" class="input_field" value=""/><div class="validate"><div class="error vpasswdError"></div><img src="/hooni/images/check.jpg" alt="" class="vpasswdImg"/></div></div>
		<div class="register_item"><div class="label">Security Codes:</div><img src="http://www.hooni.org/key" alt="" id="sc_key"></div>
		<div class="register_item"><div class="label">Verify Security Codes:</div><input name="vsc" id="vsc" maxlength="25" type="text" class="input_field" value=""/><div class="validate"><div class="error vscError"></div><img src="/hooni/images/check.jpg" alt="" class="vscImg" /></div></div>
				
		
		 <div class="register_item"><input type="submit" value="Submit" class="button"/></div>
    </form>
		<form name="register_dummy" action="register" method="post" id="register_dummy">
		
		</form>
</div>

<div id="reg_joinforfree">
   <div id="joinforfree">
			 <input type="button" name="join" value="join for FREE" class="join_button"/>
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
