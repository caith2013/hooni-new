<#include "include/macros.macro" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>${share.title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/css/styles.css" />
<!--[if IE]><style>@import url("/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/popup.js"></script>

<meta name="company" content="hooni.org" />
<meta name="copyright" content="Copyright hooni.org all rights reserved" />
<meta name="description" content="${share.offer}" />
<meta name="keywords" content="${share.title?replace(' ',',')}" />
<meta name="revisit-after" content="5 Days" />
<meta name="robots" content="index,follow" />
<meta name="title" content="${share.title}" />
<meta name="product-path" content="/share?sid=${share.id?c}" />
	<meta property="og:type" content="share"/>
	<meta property="og:site_name" content="www.hooni.org"/>
	<meta property="og:image" content="http://www.hooni.org/shares/${share.id?c}_full.jpg"/>
	<meta property="og:description" content="${share.offer}"/>
	<meta property="og:title" content="${share.title}"/>
	<meta property="og:url" content="http://www.hooni.org:80/share?sid=${share.id?c}"/>
	
</head>
<body>
<div id="container">

<@mainMenu/>


<div id="share_left_menu">
<dl>
  <dt>${share.title}</dt>
    <dd>
				${share.offer}
				<br/>
				<div class="rate_font"><#if share.price == 0>Free<#else>${share.price?string.currency}/${share.byRate}</#if></div>
		</dd>
  
</dl> 
</div>		

 <div id="share_contents">
		 			<div id="contents_left">
		 					 <div class="page_title">Shares >> ${share.title}</div>
				
							
				<div class="form_div odd_item">
        		<#list share.blogs as blog>
        
						 <div class="item_section <#if blog_index % 2 == 0>even_color<#else>odd_color</#if>">
        				<div class="label">Post on: ${blog.timeCreated?string("EEEE, MMMM dd, yyyy")}</div>
        				<div class="data">
										 <#assign x=blog.numPicture>
										 <#if (x>0)>
										 			<#list 0..x-1 as i> 
        						 						 <img src="/images/shares/${share.id?c}_${blog.id?c}_${i}_thumb.jpg" alt="" class="thumb"/>
										 			</#list>
										 </#if>
										 <br/>
										${blog.content}
										
        				</div>
        		</div>
						</#list>
						
						
						
						
					<form name="shares_input_form" action="blog" method="post" id="shares_input_form" enctype="multipart/form-data">
					<input name="sid" value="${share.id?c}" type="hidden" />
        		<div class="item_section">
        				<div class="label">Today's Post:</div>
        				<div class="data">
        						<textarea cols="85" rows="15" name="blogging"></textarea>
        				</div>
        		</div>
						
						<div class="item_section">
        				<div class="label">Picture 1:</div>
        				<div class="data">
        						 <input type="file" size="50" name="news_picture1">
        						 
        				</div>
        		</div>
						
						<div class="item_section">
        				<div class="label">Picture 2:</div>
        				<div class="data">
        						 <input type="file" size="50" name="news_picture2">
        						 
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
			 		</div><!-- end of contents_left -->
		
					<div id="contents_right">
					<div id="contact_infor">
							 <div id="contact_title">Contact information for this offer</div>
    					<#assign user=share.user>
    					<ol>
    					<li>${user.email}</li>
    					<li>${user.phone!}</li>
    					<li>${user.cell!}</li>
    					</ul>
					</div>
  					<div id="my_ad">
      
      					 <div class="ad_item">
      					 		<img src="/products/ads/iphone.jpg" alt="" />	
      					 </div>
  					 </div>	
				  </div><!-- end of contents_right -->

		
</div><!-- end of contents -->


</div><!-- end of container -->

<div id="popup"><img src="/images/key.png" alt="" id="popup_img"/></div>

<@footer />
</body>
</html>
