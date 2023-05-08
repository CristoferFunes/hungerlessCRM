package com.hungerless.HungerlessCRM.clients;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.API;
import com.hungerless.HungerlessCRM.Pair;

public class ClientsAPI extends API
{
	@Override
	public String getFrom()
	{
		return "Clients";
	}

	@Override
	public Pair<String, Object> getCondition()
	{
		return new Pair<String, Object>("customer", true);		
	}

	@Override
	public Function<QueryDocumentSnapshot, Object> mapper()
	{
		return new ClientMapper();
	}

	@Override
	public Pair<String, Object> getOrder()
	{
		return new Pair<String, Object>("last_sale_date", Query.Direction.ASCENDING);	
				
	}

	@Override
	public int getLimit()
	{
		return 9999;
	}
}

