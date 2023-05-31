package com.hungerless.HungerlessCRM.calculator;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.Pair;
import com.hungerless.HungerlessCRM.API;

public class PricesAPI extends API
{

	@Override
	public String from()
	{
		return "Prices";
	}

	@Override
	public int getTypeOfCondition()
	{
		return API.EQUALSTO;
	}

	@Override
	public Pair<String, Object> getCondition()
	{
		return new Pair<String, Object>("data_name", "Prices");	
	}

	@Override
	public Pair<String, Object> getOrder()
	{
		return null;
	}

	@Override
	public int getLimit()
	{
		return 100;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Function<QueryDocumentSnapshot, HashMap<String, Object>> mapper()
	{
		return new PricesMapper();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> HashMap<String, Object> putMapper(T t)
	{
		return (HashMap<String, Object>) t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> String idRef(T t)
	{
		return (String) ((HashMap<String, Object>) t).get("id");
	}

	@Override
	public <V> Pair<String, V> getSecondCondition()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
