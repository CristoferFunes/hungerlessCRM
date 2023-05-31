package com.hungerless.HungerlessCRM.sales;

import java.util.HashMap;
import java.util.Set;

public abstract class SalesContainer
{
	private static HashMap<String,Sale> objects = new HashMap<>();
	
	public static Sale get(String id)
	{
		return objects.get(id);		
	}
	
	public static void add(Sale s)
	{
		objects.put(s.getSaleID(), s);
	}
	
	public static Set<String> getKeySet()
	{
		return objects.keySet();
	}
	
	public static void clearSales()
	{
		objects.clear();
	}
}
