package com.hungerless.HungerlessCRM.calculator;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;

public class MultipliersMapper implements Function<QueryDocumentSnapshot, HashMap<String, Double>>
{
	private HashMap<String, Double> multiplier;
	
	@Override
	public HashMap<String, Double> apply(QueryDocumentSnapshot t)
	{
		multiplier = new HashMap<>();
		multiplier.put("calories", t.getDouble("calories"));
		multiplier.put("b_Animal", t.getDouble("b_Animal"));
		multiplier.put("b_Cereal", t.getDouble("b_Cereal"));
		multiplier.put("b_Fat", t.getDouble("b_Fat"));
		multiplier.put("b_Fruit", t.getDouble("b_Fruit"));
		multiplier.put("b_Legumes", t.getDouble("b_Legumes"));
		multiplier.put("b_Other", t.getDouble("b_Other"));
		multiplier.put("b_Vegetables", t.getDouble("b_Vegetables"));
		multiplier.put("m_Animal", t.getDouble("m_Animal"));
		multiplier.put("m_Cereal", t.getDouble("m_Cereal"));
		multiplier.put("m_Fat", t.getDouble("m_Fat"));
		multiplier.put("m_Fruit", t.getDouble("m_Fruit"));
		multiplier.put("m_Legumes", t.getDouble("m_Legumes"));
		multiplier.put("m_Other", t.getDouble("m_Other"));
		multiplier.put("m_Vegetables", t.getDouble("m_Vegetables"));
		return multiplier;
	}

}
