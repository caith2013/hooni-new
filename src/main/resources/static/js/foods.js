$(function() {
    		setupFoodSteps();
				
	});


function addNewStep(e)
{
 var current_index = $(".one_step").last().index();
 var next_index = $(".one_step").last().index() + 1;
 var one_step_clone = $(".one_step").last().clone();
 
    $(one_step_clone).find(".label").text(function(index,text){
												 return text.replace(""+current_index, ""+next_index);
						});
		
		$(one_step_clone).find("textarea").attr('name',function(index,attr){
												return attr.replace(""+current_index, ""+next_index);
		});

		$(one_step_clone).find("input:file").attr('name',function(index,attr){
												return attr.replace(""+current_index, ""+next_index);
		});

		$(one_step_clone).find("input:file").attr('id',function(index,attr){
												return attr.replace(""+current_index, ""+next_index);
		});
		
		$(one_step_clone).find("input:file").attr('value',function(index,attr){
												return attr.replace(attr, "");
		});
		
		$(one_step_clone).find("input:button").click(function(e){
								addNewStep(e);				

		});
		
		$(".one_step").last().find('.food_add_step').hide();
		$(".one_step").last().after(one_step_clone);

}



function setupFoodSteps()
{
 				 $("#addStep").click(function(e){
				 
				 			addNewStep(e);
		
				 });

		
}