$(function() {
				$("#product_left_menu a.product_search_cat").click(function() {searchProduct(this);});
				$("#product_left_menu a.product_search_brand").click(function() {searchProduct(this);});
				$("#product_left_menu a.product_search_price").click(function() {searchProduct(this);});
				$("#product_left_menu input:radio").click(function() {seachProductByRadio(this);});
				$("#product_left_menu input:reset").click(function() {resetForm();});
				$("#search_product_form").submit(function(){	 submitForm(this); return false;});
				$("#search_product_middle").submit(function(){	 submitForm(this); return false;});
				$("#search_product_middle input:reset").click(function() {resetForm();});
				$("#product_maker a.maker").click(function() {searchByBrand(this);});
				searchOnLoad();
				// $("#contents_left #product_position .s_header").click(function(){searchByHeader(this);});
});
function SearchAgent(category,brand,s_price,keywords)
{
  this.category = category;
	this.brand = brand;
	this.price = s_price;
	this.keywords = keywords;
	this.formatPrice = formatPrice;
	this.doSearch = doSearch;
	this.populateHeader = populateHeader;
	this.getSearchParams = getSearchParams;
	
}

function formatPrice(s_price)
{
 a_price = s_price.split(/::/);
 var f_price = "$" + a_price[0] + "--" + "$" + a_price[1];
 return f_price;
}

function populateHeader()
{
 var header = '<div id="hooni_header" class="search_header"><a href="/www?op=products">Hooni products</a>&nbsp;&gt;&gt;</div>';
 
 if (this.category !="")
 		header += '<div id="category_header" class="search_header"><a href="javascript:void(0);" category="'+ this.category + '" brand="" price="" keywords="" class="s_header">' + this.category + '</a>&nbsp;&gt;&gt;</div>';
 if (this.brand !="")
 		header += '<div id="brand_header" class="search_header"><a href="javascript:void(0);" category="'+ this.category + '" brand="' + this.brand + '" price="" keywords="" class="s_header">' + this.brand + '</a>&nbsp;&gt;&gt;</div>';
 if (this.price !="")
 		header += '<div id="price_header" class="search_header"><a href="javascript:void(0);" category="'+ this.category + '" brand="' + this.brand + '" price="' + this.price + '" keywords="" class="s_header">' + this.formatPrice(this.price) + '</a>&nbsp;&gt;&gt;</div>';
 if (this.keywords !="")		
		header += '<div id="keywords_header" class="search_header"><a href="javascript:void(0);" category="'+ this.category + '" brand="' + this.brand + '" price="' + this.price + '" keywords="' + this.keywords + '" class="s_header">' + this.keywords + '</a>&nbsp;&gt;&gt;</div>';

		 
 $("#contents_left_product #product_position").html(header);
 
 $("#contents_left_product #product_position .s_header").click(function(){searchByHeader(this);});
}


function doSearch()
{

 $.ajax({
						url: "/product/searchproductajax",
						cache: false,
						data: {"category" : this.category, "brand" : this.brand, "price" : this.price, "keywords" : this.keywords},
						traditional: true,
						success: function(data) {$("#contents_left_product .search_result").html(data).slideDown;}
					 });
					 
}

function getSearchParams(o)
{
 	 var category = $("#product_left_menu input[name=category]:checked").val();
   var brand = $("#product_left_menu input[name=brand]:checked").val();
   var s_price = $("#product_left_menu input[name=price]:checked").val();
	 var s_keywords;
   if (o == undefined || o == null)
   {
    s_keywords = $("#search_product_middle input.search_keywords").val();
  	if (s_keywords =='') s_keywords = $("#product_left_menu input.search_keywords").val();
  		
   }
   else
   {
   	 s_keywords = $(o).find("input.search_keywords").first().val();
   }
 
 
 
 if (category == undefined) category = "";
 if (brand == undefined) brand = "";
 if (s_price == undefined) s_price ="";	
 if (s_keywords == undefined) s_keywords = "";
 

 this.brand = brand;
 this.category = category;
 this.price = s_price;
 this.keywords = s_keywords;
}

function searchByHeader(o)
{
 var category = $(o).attr('category');
 var brand = $(o).attr('brand');
 var s_price = $(o).attr('price');
 var s_keywords = $(o).attr('keywords');
 
 if (category == undefined) category = "";
 if (brand == undefined) brand = "";
 if (s_price == undefined) s_price ="";
 if (s_keywords == undefined) s_keywords ="";	
 
 var sAgent = new SearchAgent(category,brand,s_price,s_keywords);
sAgent.doSearch();
sAgent.populateHeader();
}

function searchByBrand(o)
{
var brand = $(o).attr('value');
var category = "";
var s_price = "";
var s_keywords ="";

var sAgent = new SearchAgent(category,brand,s_price,s_keywords);
sAgent.doSearch();
sAgent.populateHeader();
}



function resetForm()
{
// $("#product_left_menu input:radio").removeAttr('checked');

 $(location).attr('href','/www?op=products');
}

function seachProductByRadio(o)
{
 
 var sAgent = new SearchAgent();
 sAgent.getSearchParams();
 sAgent.doSearch();
 sAgent.populateHeader();
}

function submitForm(o)
{
 var sAgent = new SearchAgent();
 sAgent.getSearchParams(o);
 sAgent.doSearch();
 sAgent.populateHeader();
}
function searchProduct(o)
{
 $(o).prev().attr('checked','checked');
  
 var sAgent = new SearchAgent();
 sAgent.getSearchParams();
 sAgent.doSearch();
 sAgent.populateHeader();
 

}

function searchOnLoad()
{
 var sAgent = new SearchAgent();
 sAgent.getSearchParams();

 if (sAgent.brand != "" || sAgent.category != "" || sAgent.price != "" || sAgent.keywords != "")
 {
 	sAgent.doSearch();
 	sAgent.populateHeader();
 }
}

