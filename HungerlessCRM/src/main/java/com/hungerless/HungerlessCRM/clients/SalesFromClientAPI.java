package com.hungerless.HungerlessCRM.clients;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.API;
import com.hungerless.HungerlessCRM.Pair;
import com.hungerless.HungerlessCRM.sales.SaleMapper;

public class SalesFromClientAPI extends API
{
	private String id;
	
	public SalesFromClientAPI(String id)
	{
		this.id = id;
	}
	
	@Override
	public String from()
	{
		return "Sales";
	}

	@Override
	public int getTypeOfCondition()
	{
		return API.EQUALSANDLESSOREQUAL;
	}

	@Override
	public Pair<String, Object> getCondition()
	{
		return new Pair<String, Object>("client_id", id);		
	}
	
	@Override
	public Pair<String, Object> getSecondCondition()
	{
		return new Pair<String, Object>("sale", true);		
	}

	@Override
	public Pair<String, Object> getOrder()
	{
		return null;
	}

	@Override
	public int getLimit()
	{
		// TODO Auto-generated method stub
		return 99;
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
