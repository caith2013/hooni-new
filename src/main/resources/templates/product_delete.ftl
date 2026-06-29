<#include "macros.macro" />
<#include "product.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Delete Product Confirmation</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/product.js"></script>
<script type="text/javascript" src="/hooni/js/product_search.js"></script>
</head>
<body>

<div id="container">

<@mainMenu/>
<@HomeLeftMenu/>
<@ProductLeftMenu/>

<div class="page_title">Delete Product Confirmation</div>


<div id="register_confirm" class="normal">


<p/>
<#if deleted??>
Product had been deleted successfully.
<#else>
Product had not been deleted successfully.
</#if>

</div>

<div id="product_position"></div>
							 <div class="product_row search_result">
							 </div>

</div><!-- end of container -->

<@footer />
</body>
</html>
