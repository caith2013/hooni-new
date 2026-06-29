$(function() {
			
			var full_con = $("#food_images");
			var zoom = $("#zoom");
			var view = $("#view");
			var b_img_height = 3000;
			var b_img_width = 4000;
			
    		full_con.mousemove(function(event){findPosition(event,this);});
				
				full_con.hover(function( event ){
																   zoom.fadeTo("slow", 0.5);
																	 var hz = full_con.outerHeight()/4;
																	 var wz = full_con.outerWidth()/4;
																	 zoom.css("height", hz);
																	 zoom.css("width", wz);
																	 zoom.show();
																	 zoom.css("cursor", "crosshair");
																	 view.css("height", hz*(b_img_height/full_con.outerHeight()));
																	 view.css("width", wz*(b_img_width/full_con.outerWidth()));
																	 view.show();
																	 },
													function( event ){
	 							 									zoom.hide();
																	 view.hide();
																	}
												);
					
	});
	
	function findPosition(e,o)
	{
	 var b_img_height = 3000;
	 var b_img_width = 4000;
	
	 var zoom = $("#zoom");
	 var fx = e.pageX - $(o).offset().left;
	 var fy = e.pageY - $(o).offset().top;
	
	// $("#iposition #fposition #fleft").val(fx);
	// $("#iposition #fposition #ftop").val(fy);
	 
	 
	 var zy = e.pageY - zoom.outerHeight()/2;
	 var zx = e.pageX - zoom.outerWidth()/2;
	 
	 
	 zx = Math.max(zx, $(o).offset().left );
	 zy = Math.max(zy, $(o).offset().top );
	 
	 zx = Math.min(zx,($(o).outerWidth() - zoom.outerWidth()) + $(o).offset().left);
	 zy = Math.min(zy,($(o).outerHeight() - zoom.outerHeight()) + $(o).offset().top);
	 $("#zoom").offset({top: zy, left: zx});
	 
	// $("#iposition #oposition #zleft").val(zx);
	// $("#iposition #oposition #ztop").val(zy);
	 
	 
	 var bx = zx - $(o).offset().left;
	 var by = zy - $(o).offset().top;
	 
	// $("#iposition #oposition #zpleft").val(bx);
	// $("#iposition #oposition #zptop").val(by);
	
	 bx =0 - (b_img_width/$(o).outerWidth()) * bx;
	 by =0 - (b_img_height/$(o).outerHeight()) * by;
	 
	 
	// $("#iposition #oposition #afleft").val(bx);
	 
	// $("#iposition #oposition #aftop").val(by);
	 
	 
	 var view = $("#view");
	 
	 //view.css("height", ''+$("#zoom").outerHeight()*4);
	 //view.css("width", ''+$("#zoom").outerWidth()*4);
	 view.css("background-position", ''+ bx +'px ' + by +'px');
	 
	 
	}
	