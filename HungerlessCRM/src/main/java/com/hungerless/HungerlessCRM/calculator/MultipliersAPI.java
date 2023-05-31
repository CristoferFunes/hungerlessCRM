package com.hungerless.HungerlessCRM.calculator;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.Pair;
import com.hungerless.HungerlessCRM.API;

public class MultipliersAPI extends API
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
		return new Pair<String, Object>("data_name", "Multipliers");	
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
	public Function<QueryDocumentSnapshot, HashMap<String, Double>> mapper()
	{
		return new MultipliersMapper();
	}

	@Override
	public HashMap<String, Object> putMapper(Object o)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String idRef(Object o)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> Pair<String, V> getSecondCondition()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
