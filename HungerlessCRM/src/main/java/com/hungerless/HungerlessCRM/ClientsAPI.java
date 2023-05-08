package com.hungerless.HungerlessCRM;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;

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
		return new Pair<String, Object>("Customer", true);		
	}

	@Override
	public Function<QueryDocumentSnapshot, Object> mapper()
	{
		return new ClientMapper();
	}
}

