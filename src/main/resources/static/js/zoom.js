$(function() {
			
			var full_con = $("#product_images");
			var zoom = $("#zoom");
			var view = $("#view");
				
    		full_con.mousemove(function(event){findPosition(event,this);});
				
				full_con.hover(function( event ){
																   zoom.fadeTo("slow", 0.5);
																	 zoom.show();
																	 zoom.css("cursor", "crosshair");
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
	 
	
	
	 var fx = e.pageX - $(o).offset().left;
	 var fy = e.pageY - $(o).offset().top;
	
	 //$("#iposition #fposition #fleft").val(fx);
	 //$("#iposition #fposition #ftop").val(fy);
	 
	 
	 var zy = e.pageY - $("#zoom").height()/2;
	 var zx = e.pageX - $("#zoom").width()/2;
	 
	 
	 zx = Math.max(zx, $(o).offset().left );
	 zy = Math.max(zy, $(o).offset().top );
	 
	 zx = Math.min(zx,($(o).width() - $("#zoom").width()) + $(o).offset().left);
	 zy = Math.min(zy,($(o).height() - $("#zoom").height()) + $(o).offset().top);
	 $("#zoom").offset({top: zy, left: zx});
	 
	 //$("#iposition #oposition #zleft").val(zx);
	 //$("#iposition #oposition #ztop").val(zy);
	 
	 
	 var bx = zx - $(o).offset().left;
	 var by = zy - $(o).offset().top;
	 
	 //$("#iposition #oposition #zpleft").val(bx);
	 //$("#iposition #oposition #zptop").val(by);
	
	 bx =0 - 8 * bx;
	 by =0 - 8 * by;
	 
	 
	 //$("#iposition #oposition #afleft").val(bx);
	 
	 //$("#iposition #oposition #aftop").val(by);
	 
	 
	 var view = $("#view");
	 
	 view.css("background-position", ''+ bx +'px ' + by +'px');
	 
	 /*
	 
	 var ox = fx * 8 + data.offsetX;
	 var oy = fy * 8 + data.offsetY;
	 
	 var oc = $("#original_con");
	 
	 var view = $("#view");
	 
	 //var nx =  view.offset().left;
	 //var ny = view.offset().top;
	 var nx =  view.offset().left - ox + data.offsetX;
	 var ny = view.offset().top - oy + data.offsetY;
	 
	 //nx = Math.min(nx, view.width()-data.width);
	 $("#iposition #oposition #afleft").val(nx);
	 
	 $("#iposition #oposition #aftop").val(ny);
	 oc.animate({
	 						 left: nx,
							 top: ny
		
							 },
							 1
							 );
	 
	
	 
	 $("#iposition #oposition #oleft").val(ox);
	 $("#iposition #oposition #otop").val(oy);
	 
	 
	 
	 $("#fade_img img").offset({top: oy, left: ox});
	 
	 
	 */
	}
	