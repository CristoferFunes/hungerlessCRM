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
		return new Pair<String, Object>("last_sale_date", Query.Direction.DESCENDING);	
				
	}

	@Override
	public int getLimit()
	{
		return 9999;
	}

	@Override
	public HashMap<String, Object> putMapper(Object o)
	{
		HashMap<String, Object> post = new HashMap<>();
		post.put("first_name", ((Client)o).getFirst_name());
		post.put("last_name", ((Client)o).getLast_name());
		post.put("adress", ((Client)o).getAddress());
		post.put("phone_number", ((Client)o).getPhoneNumber());
		post.put("comments", ((Client)o).getComments());
		post.put("date_of_creation", ((Client)o).getDateOfCreation());
		post.put("last_sale_date", ((Client)o).getLastSaleDate());
		post.put("prospect", ((Client)o).isProspect());
		post.put("customer", ((Client)o).isCustomer());
		
		return post;
	}

	@Override
	public String updateRef(Object o)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

