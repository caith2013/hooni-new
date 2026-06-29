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
<script type="text/javascript" src="/hooni/js/login.js"></script>
</head>
<body>
<div id="container">

<@mainMenu/>

<div class="page_title">
Request Changing Password Fail
		
		<#if errorMessage??>
		<div class="error">
		    ${errorMessage}
		</div>
		</#if>
</div>

<div class="form_div">
		 
		 <p />
		 <p />
		 <p />
		 <p>Please make a request again <a href="/forgot">here</a>.</p>
		 <p>You also can contact us <a href="http://www.hooni.org/contactus">here</a>.</p>
		
</div>



</div><!-- end of container -->
<@footer />
</body>
</html>
