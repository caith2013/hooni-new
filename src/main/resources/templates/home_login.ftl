<#include "include/macros.macro" />
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
</head>
<body>
<div id="container">

<@mainMenu/>

<div class="page_title">
Login
</div>

<div class="form_div">
		 
		 <form name="login_form" action="login" method="post" id="login_form">
		 
		 			 <div class="login_item"><div class="label">Username:</div><input name="userName" maxlength="25" type="text" value="" class="input_field"/></div>
		 			 <div class="login_item"><div class="label"></div><input name="passwd" maxlength="25" type="hidden" class="input_field" value=""/></div>
		 			 <div class="login_item"><div class="submit_button"><input type="submit" value="Login" class="button"/></div></div>
		 </form>
</div>
</div><!-- end of container -->
<@footer />
</body>
</html>
