package com.hungerless.HungerlessCRM.clients;

import java.util.HashMap;
import java.util.Set;

public abstract class ClientsContainer 
{
	private static HashMap<String,Client> objects = new HashMap<>();
	
	public static Client get(String id)
	{
		return objects.get(id);		
	}
	
	public static void add(Client c)
	{
		objects.put(c.getClientID(), c);
	}
	
	public static Set<String> getKeySet()
	{
		return objects.keySet();
	}
	
	public static void clearClients()
	{
		objects.clear();
	}
}
