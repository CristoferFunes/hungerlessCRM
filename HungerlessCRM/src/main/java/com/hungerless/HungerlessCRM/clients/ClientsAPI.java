package com.hungerless.HungerlessCRM.clients;
import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.Pair;
import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.API;

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
		post.put("address", ((Client)o).getAddress());
		post.put("phone_number", ((Client)o).getPhoneNumber());
		post.put("comments", ((Client)o).getComments());
		if(null != ((Client)o).getLastSaleSpecs()) post.put("last_sale_specs", ((Client)o).getLastSaleSpecs());
		post.put("date_of_creation", ((Client)o).getDateOfCreation());
		if(null != ((Client)o).getLastSaleDate()) post.put("last_sale_date", ((Client)o).getLastSaleDate());
		post.put("prospect", ((Client)o).isProspect());
		post.put("customer", ((Client)o).isCustomer());
		post.put("userID", StateControl.getCurrentUser().getId());
		if(((Client)o).getSeniority() != 0) post.put("seniority", ((Client)o).getSeniority());
		if(((Client)o).getAverage_ticket() != 0) post.put("average_ticket", ((Client)o).getAverage_ticket());
		if(null != ((Client)o).getLastSaleID()) post.put("last_sale_id", ((Client)o).getLastSaleID());
		return post;
	}

	@Override
	public String idRef(Object o)
	{
		return ((Client) o).getClientID();
	}

	@Override
	public <V> Pair<String, V> getSecondCondition()
	{
		// TODO Auto-generated method stub
		return null;
	}
}

