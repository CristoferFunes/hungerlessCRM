package com.hungerless.HungerlessCRM.calculator;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.API;
import com.hungerless.HungerlessCRM.Pair;
import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.sales.Sale;
import com.hungerless.HungerlessCRM.sales.SaleMapper;

public class QuotationsAPI extends API
{

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
		return new Pair<String, Object>("quotation", true);		
	}

	@Override
	public Pair<String, Object> getOrder()
	{
		return new Pair<String, Object>("date_of_Creation", Query.Direction.DESCENDING);	
	}

	@Override
	public int getLimit()
	{
		return 999;
	}

	@Override
	public Function<QueryDocumentSnapshot, Object> mapper()
	{
		return new SaleMapper();
	}

	@Override
	public HashMap<String, Object> putMapper(Object o)
	{
		HashMap<String, Object> post = new HashMap<>();
		post.put("quotation", ((Sale)o).isQuotation());
		post.put("sale", ((Sale)o).isSale());
		post.put("specs", ((Sale)o).getSpecs());
		post.put("date_of_Creation", ((Sale)o).getDateOfCreation());
		post.put("total_cost", ((Sale)o).getTotal_cost());
		post.put("tokens", ((Sale)o).getTokens());
		if(((Sale)o).getForSprint() != 0) post.put("for_sprint", ((Sale)o).getForSprint());
		post.put("weeks", ((Sale)o).getWeeks());
		post.put("client_id", ((Sale)o).getClient_id());
		post.put("client_name", ((Sale)o).getClient_name());
		post.put("products", ((Sale)o).getProducts());
		post.put("userID", StateControl.getCurrentUser().getId());
		if(((Sale)o).isLegacy()) post.put("legacy", ((Sale)o).isLegacy());
		if(((Sale)o).isConsecutiveOrder()) post.put("consecutive_order", ((Sale)o).isConsecutiveOrder());
		if(null != ((Sale)o).getParent_id()) post.put("parent_id", ((Sale)o).getParent_id());
		return post;
	}

	@Override
	public String idRef(Object o)
	{
		return ((Sale)o).getSaleID();
	}

	@Override
	public <V> Pair<String, V> getSecondCondition()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
