
$(function() {
				
    		setupForm();
				
	});
	
	function setupForm()
	{
	 	
			$("#change_pw_form").submit(function(){
						
						 encryptForm();
						 
						 return false;
						
			});
			$("input.join_button").click(function(){
							$(location).attr('href',"https://www.hooni.org/register");
			});
	}

	function encryptForm()
	{
	 			$("#change_pw_form input.input_field").each(
													function(index){
																					var key = $("#vsc").val();
												
																					var crypt = Aes.Ctr.encrypt($(this).val(), key, 256);
												
																					$(this).clone().val(crypt).appendTo("#dummy_change_pw_form");
												
													});
																	
				 $("#dummy_change_pw_form").submit();
	}
	
	