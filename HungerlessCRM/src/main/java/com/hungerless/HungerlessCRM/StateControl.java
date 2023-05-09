package com.hungerless.HungerlessCRM;

import java.util.HashSet;
import java.util.Set;

public abstract class StateControl
{
	private static String currentClient;
	private static int currentSale;
	private static int currentProduct;
	private static Set<String> selectedClients = new HashSet<>();
	
	public static void setCurrentClient(String c)
	{
		currentClient = c;
	}
	
	public static String getCurrentClient()
	{
		return currentClient;
	}
	
	public static void setCurrentSale(int s)
	{
		currentSale = s;
	}
	
	public static int getCurrentSale()
	{
		return currentSale;
	}
	
	public static void setCurrentProduct(int p)
	{
		currentProduct = p;
	}
	
	public static int getCurrentProduct()
	{
		return currentProduct;
	}
	
	public static void addSelectedClient(String c)
	{
		selectedClients.add(c);
	}
	
	public static void removeSelectedClient(String c)
	{
		selectedClients.remove(c);
	}
	
	public static void clearSelectedClients()
	{
		selectedClients.clear();
	}
	
	public static Set<String> getSelectedClients()
	{
		return selectedClients;
	}
	
	public static boolean selectedClientsEmpty()
	{
		return selectedClients.isEmpty();
	}
}
