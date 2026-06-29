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

<script language="JavaScript" type="text/javascript">
<!--

//-->
</script>

</head>
<body>

<div id="container">

<@mainMenu/>


<div class="page_title">Register</div>


<div class="form_div">
		
    <form name="register_form" action="testform" method="post" id="register_form">
    <div class="register_item"><div class="label">User Name:</div><input name="userName" maxlength="25" type="text"/></div>
    
		<div class="register_item"><div class="label">Security Code:</div><input name="sc_input" maxlength="30" type="text"/></div>
		
		<div class="register_item"><div class="label">Security Image:</div><img src="http://www.hooni.org/s_img" alt="" id="s_img"/></div>
		
		
		    <div class="register_item"><input type="submit" value="Submit" class="button"/></div>
    </form>
</div>



</div><!-- end of container -->

<@footer />
</body>
</html>
