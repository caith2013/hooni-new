
var _url = "http://www.hooni.org";


$(function() {
				
    		setupForm();
	});
	

	function setupForm()
	{
	 		$("div.validate img").hide();
	 		$("#userName").blur(function() { checkUsername(this.value); });
			$("#email").blur(function(){ 
							if (!this.value) { return reset(this.id); }
							if (! /^[a-zA-Z0-9_\~\-\.]+@[a-zA-Z0-9_\-]+(\.[a-zA-Z0-9_\-]+){1,6}$/.test(this.value))
							{
								 notOkey(this.id, "Invalid email address");
								}
        		  else
            			isOkey(this.id);
			});
			$("#passwd").blur(checkPassword);
			$("#vpasswd").blur(checkVerifyPassword);
			
			$('#vsc').blur(function() {
        if ($("#vsc").val() == "")
            reset(this.id);
        else
            isOkey(this.id);
      });
			
			$("#register_form").submit(function(){
						if($("#userName").val()=="") { notOkey('userName','can not be empty'); return false;}
						if($("#email").val()=="") { notOkey('mail','can not be empty'); return false;}
						if($("#passwd").val()=="") { notOkey('passwd','can not be empty'); return false;}
						if($("#vpasswd").val()=="") { notOkey('vpasswd','can not be empty'); return false;}
						if($("#vsc").val()=="") { notOkey('vsc','can not be empty'); return false;}
						
						encryptForm();
						
						return false;
			});
	}
	
	function encryptForm()
	{
	 			$("#register_form input.input_field").each(
													function(index){
													var key = $("#vsc").val();
													var crypt = Aes.Ctr.encrypt($(this).val(), key, 256);
													
													$(this).clone().attr("type","hidden").val(crypt).appendTo("#register_dummy");
													});
		//alert($("#register_dummy").html());
		
				//sumbit this form after encryption
			  $("#register_dummy").submit();
	}
	
	function checkPassword()
	{
	 var errorMsg = "";
	 
	 reset("vpasswd");
	 $("#vpasswd").val("");
	 if (!this.value) { return false; }
	
	if (this.value.length < 6) {
	    errorMsg = "At least 6 characters";
	} else if  (! /\d/.test(this.value)) {
	    errorMsg = "At least one number";
	} else if (/^\d+$/.test(this.value)) {
	    errorMsg = "$At least one letter";
	}
	
	if (errorMsg) {
		notOkey(this.id, errorMsg);
		return false;
	}
	else
			isOkey(this.id);
						// password is good - clear any errors
	
	return true;
	}
	
	function checkVerifyPassword()
	{
	var password		= $("#passwd")[0];
	
	 if (!this.value) {
		return;
	} else {
		if (this.value != password.value) {
		 
			notOkey(this.id, "Verify password fail");
		} else {
			isOkey(this.id);
		}
	}
	}

function checkUsername(userName)
{
	if (!userName) { return reset('userName'); }
	$.get(_url +"/checkname", { u: userName },
  	function(data){
        errorMsg = "";

		if ( ! /^[0-9A-Za-z_]{3,16}$/.test(userName) ) 
		{
		    errorMsg = "Invalid, at least three charaters long.";
					
		} else if (data == "false" )
		{
            errorMsg = "Username had been taken.";
						
     }

		if (errorMsg) 
		{
       notOkey("userName",errorMsg);
     } 
	  else {
         isOkey("userName");
        }
  	});
  	
  	
}

function isOkey(id)
	{
	  
	  $("div." + id +"Error").css("display", "none");
		$("img." + id +"Img").css("display", "block");
	}
	
	function notOkey(id,msg)
	{
	 $("div." + id +"Error").html(msg).css("display", "block");
	 $("img." + id +"Img").css("display", "none");
	
	}
	
function reset(id)
{
	$("img." + id +"Img").css("display", "none");
	$("div." + id +"Error").css("display", "none");
}