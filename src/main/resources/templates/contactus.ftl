<#include "macros.macro" />
<#include "aboutus.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Hooni::Contact Us</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/contactus.js"></script>
</head>
<body>
<div id="container">

<@mainMenu/>


<div id="right_contents">

<div id="contactus_container">
<h1>Contact Us</h1>

<p>Please email us if you have any questions. We will try to get back to you as soon as possible.</p>
<br/>
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


<form name="contactus_form" method="post" action="contactus" id="contactus">
			<div class="item_section">
        <div class="label">Department:</div>
        		 <div class="data">
              		<ul class="contact_us" id="dept_email">
              		<li><input type="radio" name="dept_email" value="sale@hooni.org"/><span class="topic">Order or Sale:</span><a href="mailto:sale@hooni.org">sale@hooni.org</a></li>
              		<li><input type="radio" name="dept_email" value="help@hooni.org"/><span class="topic">Help:</span><a href="mailto:help@hooni.org">help@hooni.org</a></li>
              		<li><input type="radio" name="dept_email" value="web@hooni.org"/><span class="topic">Our Website:</span><a href="mailto:web@hooni.org">web@hooni.org</a></li>
              		<li><input type="radio" name="dept_email" value="manager@hooni.org"/><span class="topic">Manager Team:</span><a href="mailto:manager@hooni.org">manager@hooni.org</a></li>
              		</ul>
        					<div class="validate"><div class="error dept_emailError"></div><img src="/hooni/images/check.jpg" alt="" class="dept_emailImg"/></div>
        		</div>
        
        </div>
				<div class="item_section">
						<div class="label">Your Email:</div>
        				<div class="data">
        						<input type="text" name="email" class="input_field" value="<#if contactbean??>${contactbean.email!}</#if>" id="email"/><div class="validate"><div class="error emailError"></div><img src="/hooni/images/check.jpg" alt="" class="emailImg"/></div>
        				</div>
        </div>
				<div class="item_section">	
        				<div class="label">Topic:</div>
        				<div class="data">
        						<input type="text" name="topic" class="input_field" value="<#if contactbean??>${contactbean.topic!}</#if>" id="topic"/><div class="validate"><div class="error topicError"></div><img src="/hooni/images/check.jpg" alt="" class="topicImg"/></div>
        				</div>
        		</div>
        		<div class="item_section">	
        				<div class="label">Order #:</div>
        				<div class="data">
        						<input type="text" name="order_num" value="<#if contactbean??>${contactbean.orderNum!}</#if>"/>(Optional)
        				</div>
        		</div>
						<div class="item_section">
        				<div class="label">Security Codes:</div>
        				<div class="data">
        						<img src="http://www.hooni.org/key" alt="" id="sc_key" />
        				</div>
        				
        		</div>
						<div class="item_section">
        				<div class="label">Verify Security Codes:</div>
        				<div class="data">
        						<input name="vsc" id="vsc" type="text" value="" id="vsc" class="input_field"/><div class="validate"><div class="error vscError"></div><img src="/hooni/images/check.jpg" alt="" class="vscImg"/></div>
        				</div>
        				
        		</div>
        		<div class="item_section">
        				<div class="label">Your question:</div>
        				<div class="data">
        						<textarea cols="75" rows="20" name="question" class="input_field" id="question"><#if contactbean??>${contactbean.question!}</#if></textarea><div class="validate"><div class="error questionError"></div><img src="/hooni/images/check.jpg" alt="" class="questionImg"/></div>
        				</div>
        				
        		</div>
			   		
        		<div class="item_section">
        				<div class="label empty"></div>
        				<div class="data">
        						<input type="submit" name="submit" value="Send" />
        				</div>
        				
        		</div>
</form>

</div><!-- contactus_container end here -->

</div><!-- end of right_contents -->

</div><!-- end of container -->

<@footer />
</body>
</html>
