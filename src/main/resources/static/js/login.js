
$(function() {
				
    		setupForm();
				
	});
	
	function setupForm()
	{
	 	
			$("#login_form").submit(function(){
						
						 encryptForm();
						 
						 return false;
						
			});
			$("input.join_button").click(function(){
							$(location).attr('href',"/register");
			});
	}

	function encryptForm()
	{
	 			$("#login_form input.input_field").each(
													function(index){
																					var key = $("#vsc").val();
												
																					var crypt = Aes.Ctr.encrypt($(this).val(), key, 256);
												
																					$(this).clone().val(crypt).appendTo("#login_dummy");
												
													});
																	
				 $("#login_dummy").submit();
	}
	
	