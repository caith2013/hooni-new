package com.hooni.web.conf;

public enum ControllerUrl
{
	WWW("com.hooni.web.http.controllers.HomePageController"), 
	WIGS("com.hooni.web.http.controllers.wigs.WigsHomeController"),
	FOOD("com.hooni.web.http.controllers.food.FoodHomeController"),
	REGISTER("com.hooni.web.http.controllers.RegisterController"), 
	LOGIN("com.hooni.web.http.controllers.LoginController"), 
	LOGOUT("com.hooni.web.http.controllers.LogoutController"),
	FOODS("com.hooni.web.http.controllers.FoodsController"),
	ADS("com.hooni.web.http.controllers.AdsController"),
	ADSREPLY("com.hooni.web.http.controllers.AdsReplyController"),
	KEYWORDS("com.hooni.web.http.controllers.KeywordsController"),
	NEWS("com.hooni.web.http.controllers.NewsController"),
	SHARE("com.hooni.web.http.controllers.ShareController"),
	BLOG("com.hooni.web.http.controllers.BlogController"),
	PRODUCT("com.hooni.web.http.controllers.ProductController"),
	SHOPPINGCART("com.hooni.web.http.controllers.ShoppingCartController"),
	PAYPAL("com.hooni.paypal.PaypalController"),
	ORDERCONFIRM("com.hooni.paypal.OrderConfirmController"),
	WARRANTYAJAX("com.hooni.web.http.controllers.WarrantyAjaxController"),
	SHIPPINGAJAX("com.hooni.web.http.controllers.ShippingAjaxController"),
	SHIPPINGCOSTAJAX("com.hooni.web.http.controllers.ShippingCostAjaxController"),
	SHOPPINGCARTAJAX("com.hooni.web.http.controllers.ShoppingCartAjaxController"),
	TESTFORM("com.hooni.web.http.controllers.testController"),
	ACTIVATE("com.hooni.web.http.controllers.ActivateController"),
	KEY("com.hooni.web.http.controllers.KeyController"),
	CHECKNAME("com.hooni.web.http.controllers.CheckUserNameController"),
	FOOTER("com.hooni.web.http.controllers.FooterController"),
	CONTACTUS("com.hooni.web.http.controllers.ContactUsController"),
	ERROR("com.hooni.web.error.ErrorController"),
	SEARCHFOODAJAX("com.hooni.web.http.controllers.SearchFoodAjaxController"),
	SEARCHPRODUCTAJAX("com.hooni.web.http.controllers.SearchProductAjaxController"),
	ADDTOMEALPLANAJAX("com.hooni.web.http.controllers.AddToMealPlanAjaxController"),
	MEALPLANAJAX("com.hooni.web.http.controllers.MealPlanAjaxController"),
	FAVORITEAJAX("com.hooni.web.http.controllers.FavoriteAjaxController"),
	STARTCOOKING("com.hooni.web.http.controllers.food.StartCookingController"),
	FORGOTPASSWORD("com.hooni.web.http.controllers.ForgotPasswordController"),
	CHANGEPASSWORD("com.hooni.web.http.controllers.ChangePasswordController"),
	PRODUCTMANAGER("com.hooni.web.http.controllers.ProductManagerController"),
	FILTERCABLETOGO("com.hooni.web.http.controllers.FilterCableToGoController"),
	ADMORDERS("com.hooni.web.http.controllers.adm.AdmCurrentOrdersController"),
	TEST("com.hooni.web.http.controllers.testController");

	private ControllerUrl(String controllerClass)
	{
		_controllerClass = controllerClass;
	}

	public String getControllerClass()
	{
		return _controllerClass;
	}

	private String _controllerClass;

	public static final ControllerUrl[] VALUES = values();
}
