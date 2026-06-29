$(function() {
			
			var o_left_img = $("#left_img");
			var zoom = $("#zoom");
			zoom.fadeTo("slow", 0.5);

				
    		$("#left_img").mousemove(function(event){findPosition(event,this);});
				
				o_left_img.hover(function( event ){
																	 zoom.show();
																	 },
													function( event ){
	 							 									zoom.hide();
																	}
												);
					
	});
	
	function findPosition(e,o)
	{
	 
		
	 var fx = e.pageX - $(o).offset().left;
	 var fy = e.pageY - $(o).offset().top;
	
	 var zy = e.pageY - $("#zoom").height()/2;
	 var zx = e.pageX - $("#zoom").width()/2;
	 
	 $("#zoom").offset({top: zy, left: zx});
	 
	 var ox = fx * 8 + $("#right_img img").offset().left;
	 var oy = fy * 8 + $("#right_img img").offset().top;
	 
	 $("#iposition #fposition #fleft").val(fx);
	 $("#iposition #fposition #ftop").val(fy);
	 
	 $("#iposition #oposition #oleft").val(ox);
	 $("#iposition #oposition #otop").val(oy);
	 
	 $("#fade_img img").offset({top: oy, left: ox});
	}
	