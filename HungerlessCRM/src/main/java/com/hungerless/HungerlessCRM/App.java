package com.hungerless.HungerlessCRM;

import com.hungerless.HungerlessCRM.GUI.Fra_main;
import com.hungerless.HungerlessCRM.GUIlogin.Fra_login;
import com.hungerless.HungerlessCRM.calculator.PricesContainer;
import com.hungerless.HungerlessCRM.calculator.ProductItem;
import com.hungerless.HungerlessCRM.login.User;

public class App
{
	public static void main(String[] args)
	{	
		StateControl.setCurrentUser(new User("T5ApYtUObfq9t3KUrjar", true, true, "Cristofer Funes"));
		new Fra_main();
		//new Fra_login();
		/*PricesContainer.updatePrices();
		ProductItem test = new ProductItem(ProductItem.BUILDERMEAL, false);*/
	}

}
