package com.hungerless.HungerlessCRM.clients;

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
				t.getString("last_sale_specs") == null ? null: t.getString("last_sale_specs"),
				t.getDate("date_of_creation"),
				t.getDate("last_sale_date") == null ? null : t.getDate("last_sale_date"),
				t.getBoolean("prospect") == null ? false : t.getBoolean("prospect"),
				t.getBoolean("customer") == null ? false : t.getBoolean("customer"),
				t.getDouble("seniority") == null ? 0 : t.getDouble("seniority"),
				t.getDouble("average_ticket") == null ? 0 : t.getDouble("average_ticket"),
				t.getString("last_sale_id") == null ? null :  t.getString("last_sale_id")
				);
	}
	
}
