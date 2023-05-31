package com.hungerless.HungerlessCRM.sales;

import java.util.ArrayList;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;

public class SaleMapper implements Function<QueryDocumentSnapshot, Object>
{
	@SuppressWarnings("unchecked")
	@Override
	public Object apply(QueryDocumentSnapshot t)
	{
		return new Sale(
				t.getId(),
				t.getBoolean("quotation") == null ? false : t.getBoolean("quotation"),
				t.getBoolean("sale") == null ? false : t.getBoolean("sale"),
				t.getString("specs"),
				t.getDate("date_of_Creation"),
				t.getDouble("total_cost"),
				t.getDouble("tokens"),
				t.getDouble("for_sprint") == null ? 0 : t.getDouble("for_sprint"),
				t.getDouble("weeks"),
				t.getString("client_id"),
				t.getString("client_name"),
				((ArrayList<String>)t.get("products")),
				t.getBoolean("legacy") == null ? false : t.getBoolean("legacy"),
				t.getBoolean("consecutive_order") == null ? false : t.getBoolean("consecutive_order"),
				t.getString("parent_id") == null ? null : t.getString("parent_id")
				);
	}
}
	