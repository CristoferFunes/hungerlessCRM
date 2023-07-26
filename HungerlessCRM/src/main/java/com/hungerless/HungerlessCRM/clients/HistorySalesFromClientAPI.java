package com.hungerless.HungerlessCRM.clients;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.Pair;
import com.hungerless.HungerlessCRM.sales.SaleMapper;
import com.hungerless.HungerlessCRM.API;

public class HistorySalesFromClientAPI extends API
{
	private String id;
	SimpleDateFormat format = new SimpleDateFormat("yyyyww");	
	
	public HistorySalesFromClientAPI(String id)
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
		return new Pair<String, Object>("for_sprint", (int)Integer.valueOf(format.format(new Date())));		
	}

	@Override
	public Pair<String, Object> getOrder()
	{
		return null;
	}

	@Override
	public int getLimit()
	{
		return 99;
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



}
