package com.hungerless.HungerlessCRM.clients;

import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;

public class ClientMapper implements Function<QueryDocumentSnapshot, Object>
{
	@Override
	public Object apply(QueryDocumentSnapshot t)
	{
		return null;
	}
}
