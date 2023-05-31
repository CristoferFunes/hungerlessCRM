package com.hungerless.HungerlessCRM.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public static HashMap<String,Double> getMultipliers(int c)
	{
		return multipliers.get(c);
	}
}