package com.hungerless.HungerlessCRM;

import java.util.Calendar;
import java.util.Date;

import com.hungerless.HungerlessCRM.GUI.Fra_main;
import com.hungerless.HungerlessCRM.clients.Client;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;
import com.hungerless.HungerlessCRM.clients.ProspectsAPI;

public class App
{
	public static void main(String[] args)
	{	
		//System.out.println(((Client) new ProspectsAPI().get().get(0)).getFirst_name());
		new Fra_main();		
	}

}
