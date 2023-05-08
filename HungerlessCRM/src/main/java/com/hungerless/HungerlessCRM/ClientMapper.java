package com.hungerless.HungerlessCRM;

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
