<#include "macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hooni: where do you want to go today?</title>
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

		 					 <div class="page_title">Welcome...It is nice to see you here.</div>
							 <div id="activate_msg">
									 Hi ${username},
									 <p/>
									 <br/>
									 <#if msg=="success">
									 Your account has been activated.									
									 Please click <a href="http://www.hooni.org/login">here</a> to login.
									 <#else>
									 Oops, we are unable to activate your account, either your account was opened for a long time or our end issue. You should receive a new 
									 email to reactivate again.
									 </#if>
									 <br/>
									 <br/>
							 </div>

							 <@footer />
							 
			 		</div><!-- end of contents_left -->
		
					<@contentsRight/>
		
		</div><!-- end of contents -->



</div><!-- end of container -->



</body>
</html>
