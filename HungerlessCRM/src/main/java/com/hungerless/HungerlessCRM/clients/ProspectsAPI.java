package com.hungerless.HungerlessCRM.clients;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.API;
import com.hungerless.HungerlessCRM.Pair;

public class ProspectsAPI extends API
{

	@Override
	public String from()
	{
		return "Clients";
	}
	
	@Override
	public int getTypeOfCondition()
	{
		return API.EQUALSTO;
	}

	@Override
	public Pair<String, Object> getCondition()
	{
		return new Pair<String, Object>("prospect", true);		
	}

	@Override
	public Function<QueryDocumentSnapshot, Object> mapper()
	{
		return new ClientMapper();
	}

	@Override
	public Pair<String, Object> getOrder()
	{
		return new Pair<String, Object>("date_of_creation", Query.Direction.DESCENDING);	
				
	}

	@Override
	public int getLimit()
	{
		return 9999;
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
