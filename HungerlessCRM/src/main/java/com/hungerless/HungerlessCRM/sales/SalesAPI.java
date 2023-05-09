package com.hungerless.HungerlessCRM.sales;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.API;
import com.hungerless.HungerlessCRM.Pair;

public class SalesAPI extends API
{
	@Override
	public String from()
	{
		return "Sales";
	}
	
	@Override
	public int getTypeOfCondition()
	{
		return API.GREATERTHAN;
	}

	@Override
	public Pair<String, Object> getCondition()
	{
		return new Pair<String, Object>("client_id", 5);
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
		return 0;
	}

	@Override
	public Function<QueryDocumentSnapshot, Object> mapper()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Object> putMapper(Object o)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateRef(Object o)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
