$(function() {
				
    		setupForm();
	});
	
	function setupForm()
	{
	 $("div.validate img").hide();
	
	 $("#email").focus(function(){
	 								
					if ($("input[name='dept_email']").is(':checked'))
					{						 
					
						 isOkey('dept_email'); 
					}
					else
					{
					 		notOkey('dept_email','can not be empty');
					}
					
														 
	  });
															 
	 $("#email").blur(function(){ 
							if (!this.value) { return reset(this.id); }
							if (! /^[a-zA-Z0-9_\~\-\.]+@[a-zA-Z0-9_\-]+(\.[a-zA-Z0-9_\-]+){1,6}$/.test(this.value))
							{
								 notOkey(this.id, "Invalid email address");
								}
        		  else
            			isOkey(this.id);
			});
	
	$('#vsc').blur(function() {
        if ($("#vsc").val() == "")
            reset(this.id);
        else
            isOkey(this.id);
	});
	
	$("#topic").blur(function() {
        if ($("#topic").val() == "")
            reset(this.id);
        else
            isOkey(this.id);
	});
	
	$("#question").blur(function() {
        if ($("#question").val() == "")
            reset(this.id);
        else
            isOkey(this.id);
	});
	
			$("#contactus").submit(function(){
						if(!$("input[name='dept_email']").is(':checked')) { notOkey('dept_email','can not be empty'); return false;}
						if($("#email").val()=="") { notOkey('email','can not be empty'); return false;}
						
						if($("#topic").val()=="") { notOkey('topic','can not be empty'); return false;}
						if($("#vsc").val()=="") { notOkey('vsc','can not be empty'); return false;}
						if($("#question").val()=="") { notOkey('question','can not be empty'); return false;}
						
						return true;
						
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