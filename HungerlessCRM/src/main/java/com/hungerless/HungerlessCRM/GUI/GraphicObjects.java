package com.hungerless.HungerlessCRM.GUI;

import java.util.HashMap;
import java.util.Set;

import javax.swing.JComponent;

import com.hungerless.HungerlessCRM.clients.ClientsContainer;
import com.hungerless.HungerlessCRM.sales.SalesContainer;
import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.calculator.ProductsContainer;


public abstract class GraphicObjects
{
	private static HashMap<String, JComponent> objects = new HashMap<>();
	
	public static JComponent get(String id)
	{
		return objects.get(id);		
	}
	
	public static void add(String id, JComponent c)
	{
		objects.put(id, c);
	}
	
	public static Set<String> getKeySet()
	{
		return objects.keySet();
	}
	
	public static void dropR()
	{
		objects.keySet().stream()
						.filter(k -> k.substring(4, 5).equals("R"))
						.forEach(r -> objects.get("Pan_workSpace").remove(objects.get(r)));
		objects.keySet().removeIf(k -> k.substring(4, 5).equals("R"));
		objects.get("Pan_workSpace").repaint();
	}
	
	public static void softDropL()
	{
		objects.keySet().stream()
						.filter(k -> k.substring(4, 5).equals("R") || k.substring(4, 5).equals("L"))
						.forEach(l -> objects.get("Pan_workSpace").remove(objects.get(l)));
		objects.keySet().removeIf(k -> k.substring(4, 5).equals("R") || k.substring(4, 5).equals("L"));
		objects.get("Pan_workSpace").repaint();
	}
	
	public static void dropL()
	{
		objects.keySet().stream()
						.filter(k -> k.substring(4, 5).equals("R") || k.substring(4, 5).equals("L"))
						.forEach(l -> objects.get("Pan_workSpace").remove(objects.get(l)));
		objects.keySet().removeIf(k -> k.substring(4, 5).equals("R") || k.substring(4, 5).equals("L"));
		StateControl.setCurrentClient(null);
		StateControl.setCurrentSale(null);
		StateControl.setCurrentProduct(0);
		StateControl.clearSelectedClients();
		ProductsContainer.clearProducts();
		ClientsContainer.clearClients();
		SalesContainer.clearSales();
		objects.get("Pan_workSpace").repaint();
	}
	
	public static void dropAll()
	{
		objects.keySet().stream().filter(k -> !k.substring(4, 5).equals("M")).forEach(a -> objects.get("Pan_Main").remove(objects.get(a)));
		objects.keySet().removeIf(k -> !k.substring(4, 5).equals("M"));
		objects.get("Pan_Main").repaint();
	}
	
	public static boolean keyExist(String k)
	{
		return objects.containsKey(k);
	}
}
