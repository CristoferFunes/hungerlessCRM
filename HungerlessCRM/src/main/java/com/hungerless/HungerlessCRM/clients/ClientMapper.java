package com.hungerless.HungerlessCRM.clients;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;

public class ClientMapper implements Function<QueryDocumentSnapshot, Object>
{
	@Override
	public Object apply(QueryDocumentSnapshot t)
	{
		return new Client(
				t.getId(),
				t.getString("first_name"),
				t.getString("last_name"),
				t.getString("address"),
				t.getString("phone_number"),
				t.getString("comments"),
				t.getDate("date_of_creation"),
				t.getDate("last_sale_date") == null ? Calendar.getInstance().getTime() : t.getDate("last_sale_date"),
				t.getBoolean("prospect") == null ? false : t.getBoolean("prospect"),
				t.getBoolean("customer") == null ? false : t.getBoolean("customer")
				);
	}
	
}
