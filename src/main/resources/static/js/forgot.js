
$(function() {
				
    		setupForm();
				
	});
	
	function setupForm()
	{
	 	
			$("#forgot_form").submit(function(){
						
						 encryptForm();
						 
						 return false;
						
			});
			$("input.join_button").click(function(){
							$(location).attr('href',"https://www.hooni.org/register");
			});
	}

	function encryptForm()
	{
	 			$("#forgot_form input.input_field").each(
													function(index){
																					var key = $("#vsc").val();
												
																					var crypt = Aes.Ctr.encrypt($(this).val(), key, 256);
												
																					$(this).clone().val(crypt).appendTo("#forgot_dummy");
												
													});
																	
				 $("#forgot_dummy").submit();
	}
	
	