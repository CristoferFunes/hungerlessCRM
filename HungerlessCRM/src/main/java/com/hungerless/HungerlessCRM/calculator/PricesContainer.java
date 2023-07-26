package com.hungerless.HungerlessCRM.calculator;

import java.util.HashMap;
import java.util.Set;

public abstract class PricesContainer
{
	private static HashMap<String, Double> prices;
	private static HashMap<Integer, HashMap<String,Double>> multipliers = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static void updatePrices()
	{
		prices = new HashMap<String, Double>(((HashMap<String, Double>)new PricesAPI().get().get(0)));
		new MultipliersAPI().get().stream().forEach(m -> multipliers.put((int) Math.round(((HashMap<String, Double>)m).get("calories")), (HashMap<String, Double>) m));
	}
	
	public static double get(String v)
	{
		return prices.get(v);
	}
	
	public static void update(String k, double v)
	{
		prices.put(k, v);
	}
	
	public static HashMap<String,Double> getMultipliers(int c)
	{
		return multipliers.get(c);
	}
	
	public static HashMap<String, Double> getAllPrices()
	{
		return prices;
	}
	
	public static Set<String> getKeySet()
	{
		return prices.keySet();
	}
}