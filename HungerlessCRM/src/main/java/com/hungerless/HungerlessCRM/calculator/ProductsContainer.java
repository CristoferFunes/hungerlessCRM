package com.hungerless.HungerlessCRM.calculator;

import java.util.HashMap;
import java.util.Set;

public abstract class ProductsContainer
{
private static HashMap<Integer, ProductItem> objects = new HashMap<>();
	
	public static ProductItem get(int id)
	{
		return objects.get(id);		
	}
	
	public static void add(ProductItem p)
	{
		objects.put(p.getProductID(), p);
	}
	
	public static Set<Integer> getKeySet()
	{
		return objects.keySet();
	}
	
	public static void clearProducts()
	{
		objects.clear();
	}
	
	public static void remove(int p)
	{
		objects.remove(p);
	}
}
