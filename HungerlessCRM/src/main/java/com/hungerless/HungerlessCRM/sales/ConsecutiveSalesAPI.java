package com.hungerless.HungerlessCRM.sales;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.API;
import com.hungerless.HungerlessCRM.Pair;

public class ConsecutiveSalesAPI extends API
{
	String parentSaleID;
	
	public ConsecutiveSalesAPI(String id)
	{
		this.parentSaleID = id;
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
		return new Pair<String, Object>("parent_id", this.parentSaleID);	
	}

	@Override
	public Pair<String, Object> getOrder()
	{
		return null;
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
		return null;
	}

}