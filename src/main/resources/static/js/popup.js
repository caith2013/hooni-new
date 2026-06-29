$(document).ready(function() {
		setupPage();
});

function setupPage()
{
$("img.thumb").each(function(i){

							$(this).bind('mouseover', function(e){
												var x = e.pageX - this.offsetLeft - 50;
												var y = e.pageY - this.offsetTop - 50;
												var image_name = $(this).attr('src');
												image_name = image_name.substring(0,image_name.indexOf('thumb'));
												image_name = image_name + 'full.jpg';
												$("#popup").css('display', 'block');
												$("#popup").css('top', y);
												$("#popup").css('left', x);
												$("#popup #popup_img").attr('src',image_name);
							
							});
							$("#popup #popup_img").bind('mouseout', function(e){
												$("#popup").css('display', 'none');
							});

});
 
}