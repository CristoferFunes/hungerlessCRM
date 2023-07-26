package com.hungerless.HungerlessCRM.sales;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.API;
import com.hungerless.HungerlessCRM.Pair;

public class HistorySalesAPI extends API
{
	private int for_sprint;
	
	public HistorySalesAPI(int sprint)
	{
		this.for_sprint = sprint;
	}
	
	@Override
	public String from()
	{
		return "Sales";
	}

	@Override
	public int getTypeOfCondition()
	{
		return API.EQUALSTO;
	}

	@Override
	public Pair<String, Object> getCondition()
	{
		return new Pair<String, Object>("for_sprint", this.for_sprint);	
	}

	@Override
	public <V> Pair<String, V> getSecondCondition()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<String, Object> getOrder()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLimit()
	{
		// TODO Auto-generated method stub
		return 999;
	}

	@Override
	public Function<QueryDocumentSnapshot, Object> mapper()
	{
		return new SaleMapper();
	}

	@Override
	public <T> HashMap<String, Object> putMapper(T t)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> String idRef(T t)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
